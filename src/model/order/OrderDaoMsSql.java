package model.order;

import exception.DataAccessException;
import exception.DataWriteException;
import model.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoMsSql implements OrderDao {
    private static final String FIND_ALL_BY_PROJECT_ID_Q = "SELECT * FROM GetOrders WHERE projectId = ?";
    private PreparedStatement findAllByProjectIdPS;
    private static final String FIND_BY_ID_Q = "SELECT * FROM GetOrders WHERE orderId = ?";
    private PreparedStatement findByIdPS;
    private static final String INSERT_ORDER_Q = "";
    private PreparedStatement insertOrderPS;
    private static final String INSERT_ORDER_LINE_Q = "";
    private PreparedStatement insertOrderLinePS;
    private static final String INSERT_ORDER_INVOICE_Q = "";
    private PreparedStatement insertOrderInvoicePS;

    public OrderDaoMsSql() {
        init();
    }

    private void init() {
        final DBConnection con = DBConnection.getInstance();

        try {
            findAllByProjectIdPS = con.prepareStatement(FIND_ALL_BY_PROJECT_ID_Q);
            findByIdPS = con.prepareStatement(FIND_BY_ID_Q);
            insertOrderPS = con.prepareStatement(INSERT_ORDER_Q, Statement.RETURN_GENERATED_KEYS);
            insertOrderLinePS = con.prepareStatement(INSERT_ORDER_LINE_Q);
            insertOrderInvoicePS = con.prepareStatement(INSERT_ORDER_INVOICE_Q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private OrderDto buildObject(ResultSet rs) {
        final OrderDto order = new OrderDto();

        try {
            order.setId(rs.getInt("orderId"));
            order.setStatus(OrderStatus.values()[(rs.getInt("orderStatus")) - 1]);
            // TODO: Fix this
            order.setCreatedAt(LocalDateTime.now());
            order.setCustomerId(rs.getInt("customerId"));
            order.setEmployeeId(rs.getInt("employeeId"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }


    private List<OrderDto> buildObjects(ResultSet rs) {
        final List<OrderDto> orders = new ArrayList<>();

        try {
            while (rs.next()) {
                orders.add(buildObject(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    @Override
    public List<OrderDto> findAllByProjectId(int id) throws DataAccessException {
        final List<OrderDto> orders;

        try {
            findAllByProjectIdPS.setInt(1, id);
            ResultSet rs = findAllByProjectIdPS.executeQuery();
            orders = buildObjects(rs);
        } catch (SQLException e) {
            e.printStackTrace();

            throw new DataAccessException("Unable to search in the database");
        }

        return orders;
    }

    @Override
    public OrderDto findById(int id) throws DataAccessException {
        final OrderDto order;

        try {
            findByIdPS.setInt(1, id);
            ResultSet rs = findByIdPS.executeQuery();

            if (!rs.next()) {
                throw new DataAccessException("Unable to find any order with this ID");
            }

            order = buildObject(rs);
        } catch (SQLException e) {
            e.printStackTrace();

            throw new DataAccessException("Unable to find any order with this ID");
        }

        return order;
    }

    @Override
    public Order create(LocalDateTime createdAt, OrderStatus status, int customerId, int employeeId, int projectId)
            throws DataWriteException {
        final Order order = new Order();

        try {
            insertOrderPS.setTimestamp(1, Timestamp.valueOf(createdAt));
            insertOrderPS.setInt(2, customerId);
            insertOrderPS.setInt(3, employeeId);
            insertOrderPS.setInt(4, projectId);
            insertOrderPS.setInt(5, OrderStatus.AWAITING.getValue());
            insertOrderPS.executeQuery();

            final int id = insertOrderPS.getGeneratedKeys().getInt(1);
            order.setId(id);
            order.setDate(createdAt);
            order.setStatus(OrderStatus.AWAITING);
        } catch(SQLException e) {
            e.printStackTrace();

            throw new DataWriteException("Unable to create order");
        }

        return order;
    }
}
