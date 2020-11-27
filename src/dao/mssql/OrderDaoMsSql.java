package dao.mssql;

import dao.*;
import datasource.DBConnection;
import entity.*;
import exception.DataAccessException;
import exception.DataWriteException;
import util.ConnectionThread;
import util.SQLDateConverter;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class OrderDaoMsSql implements OrderDao {
    private static final String FIND_BY_ID_Q = "SELECT * FROM GetOrders WHERE orderId = ?";
    private PreparedStatement findByIdPS;
    private static final String INSERT_Q = "";
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
        final int id = rs.getInt("id");
        final OrderStatus status = OrderStatus.values()[rs.getInt("status")];
        final LocalDateTime createdAt = SQLDateConverter.timestampToLocalDateTime(
                rs.getTimestamp("createdDate"));
        final Order order = new Order(id, createdAt, status);

        if (fullAssociation) {
            // Catch exception later
            AtomicReference<DataAccessException> exception = new AtomicReference<>();
            // Setup objects
            AtomicReference<Customer> customer = new AtomicReference<>();
            AtomicReference<Employee> employee = new AtomicReference<>();
            AtomicReference<OrderInvoice> invoice = new AtomicReference<>();
            AtomicReference<List<OrderLine>> orderLines = new AtomicReference<>();
            // Setup ids
            int customerId = rs.getInt("customerId");
            int employeeId = rs.getInt("employeeId");

            List<Thread> threads = new LinkedList<>();
            threads.add(new ConnectionThread(conn -> {
                CustomerDao dao = new CustomerDaoMsSql(conn);
                try {
                    customer.set(dao.findById(customerId));
                } catch (DataAccessException e) {
                    exception.set(e);
                }
            }));
            threads.add(new ConnectionThread(conn -> {
                EmployeeDao dao = new EmployeeDaoMsSql(conn);
                try {
                    employee.set(dao.findById(employeeId));
                } catch (DataAccessException e) {
                    exception.set(e);
                }
            }));
            threads.add(new ConnectionThread(conn -> {
                OrderInvoiceDao dao = new OrderInvoiceDaoMsSql(conn);
                try {
                    invoice.set(dao.findByOrderId(id));
                } catch (DataAccessException e) {
                    exception.set(e);
                }
            }));
            threads.add(new ConnectionThread(conn -> {
                OrderLineDao dao = new OrderLineDaoMsSql(conn);
                try {
                    orderLines.set(dao.findByOrderId(id));
                } catch (DataAccessException e) {
                    exception.set(e);
                }
            }));

            for (Thread t : threads)
                t.start();

            for (Thread t : threads) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    throw new DataAccessException("An error occurred while trying to find an order");
                }
            }

            if (exception.get() != null) {
                throw exception.get();
            }

            order.setCustomer(customer.get());
            order.setEmployee(employee.get());
            order.setOrderInvoice(invoice.get());
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
    public Order create(Order order) throws DataWriteException {
        return create(order.getDate(), order.getStatus(), order.getCustomer().getId(),
                order.getEmployee().getId(), 1);
    }

    @Override
    public Order create(Order order, Project project) {
        return null;
    }

    @Override
    public Order create(LocalDateTime createdAt, OrderStatus status, int customerId, int employeeId, int projectId)
            throws DataWriteException {
        final Order order = new Order();

        try {
            insertPS.setTimestamp(1, Timestamp.valueOf(createdAt));
            insertPS.setInt(2, customerId);
            insertPS.setInt(3, employeeId);
            insertPS.setInt(4, projectId);
            insertPS.setInt(5, OrderStatus.AWAITING.getValue());
            insertPS.executeQuery();

            final int id = insertPS.getGeneratedKeys().getInt(1);
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
