package dao.mssql;

import dao.OrderDao;
import dao.OrderLineDao;
import datasource.DBConnection;
import datasource.DBManager;
import datasource.DataAccessException;
import model.Order;
import model.OrderLine;
import model.Project;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class OrderDaoMsSql implements OrderDao {
    private static final String FIND_BY_ID_Q = "SELECT project_id, delivered, created_at FROM [order] " +
            "WHERE project_id = ?";
    private PreparedStatement findByIdPS;
    private static final String INSERT_Q = "INSERT INTO [order](project_id, delivered, created_at) " +
            "VALUES (?, ?, ?)";
    private PreparedStatement insertPS;
    private DBConnection connection;

    public OrderDaoMsSql(DBConnection conn) {
        init(conn);
    }

    private void init(DBConnection conn) {
        connection = conn;

        try {
            findByIdPS = conn.prepareStatement(FIND_BY_ID_Q);
            insertPS = conn.prepareStatement(INSERT_Q, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Order buildObject(final ResultSet rs, boolean fullAssociation) throws SQLException, DataAccessException {
        final int id = rs.getInt("project_id");
        final boolean delivered = rs.getBoolean("delivered");
        final LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        final Order order = new Order(id, createdAt, delivered);

        if (fullAssociation) {
            // Setup variables
            AtomicReference<DataAccessException> exception = new AtomicReference<>();
            AtomicReference<List<OrderLine>> orderLines = new AtomicReference<>();

            Thread thread = DBManager.getInstance().getConnectionThread(conn -> {
                OrderLineDao dao = conn.getDaoFactory().createOrderLineDao();
                try {
                    orderLines.set(dao.findByOrderId(id));
                } catch (DataAccessException e) {
                    exception.set(e);
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new DataAccessException("An error occurred while trying to find an order");
            }

            if (exception.get() != null) {
                throw exception.get();
            }

            orderLines.get().forEach(order::addOrderLine);
        }

        return order;
    }


    private List<Order> buildObjects(ResultSet rs, boolean fullAssociation) throws SQLException, DataAccessException {
        final List<Order> orders = new LinkedList<>();

        while (rs.next()) {
            orders.add(buildObject(rs, fullAssociation));
        }

        return orders;
    }

    @Override
    public Order findById(int id, boolean fullAssociation) throws DataAccessException {
        Order order = null;

        try {
            findByIdPS.setInt(1, id);
            ResultSet rs = findByIdPS.executeQuery();

            if (rs.next()) {
                order = buildObject(rs, fullAssociation);
            }
        } catch (SQLException e) {
            e.printStackTrace();

            throw new DataAccessException("Unable to find any order with this ID");
        }

        return order;
    }


    @Override
    public Order create(Order order, Project project) throws DataAccessException {
        try {
            insertPS.setInt(1, project.getId());
            insertPS.setBoolean(2, order.isDelivered());
            insertPS.setTimestamp(3, Timestamp.valueOf(order.getDate()));
            insertPS.execute();
            
            order.setId(project.getId());

            OrderLineDao orderLineDao = connection.getDaoFactory().createOrderLineDao();
            for (OrderLine orderLine : order.getOrderLines().values()) {
                orderLineDao.create(order, orderLine);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Unable to create order");
        }

        return order;
    }
}
