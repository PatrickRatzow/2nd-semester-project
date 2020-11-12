package database;

import model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        DBConnection con = DBConnection.getInstance();

        try {
            findAllByProjectIdPS = con.prepareStatement(FIND_ALL_BY_PROJECT_ID_Q);
            insertOrderPS = con.prepareStatement(INSERT_ORDER_Q, Statement.RETURN_GENERATED_KEYS);
            insertOrderLinePS = con.prepareStatement(INSERT_ORDER_LINE_Q);
            insertOrderInvoicePS = con.prepareStatement(INSERT_ORDER_INVOICE_Q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Order buildObject(ResultSet rs) {
        final Order order = new Order();

        try {
            // Fuck we need to do a lot of shit here
            order.setId(rs.getInt("orderId"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }


    private List<Order> buildObjects(ResultSet rs) {
        final List<Order> orders = new ArrayList<>();

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
    public List<Order> findAllByProjectId(int id) throws DataAccessException {
        final List<Order> orders;

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
    public Order findById(int id) throws DataAccessException {
        final Order order;

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
    public Order create(LocalDateTime createdAt, Set<OrderLine> orderLines, Set<OrderInvoice> invoices,
                        Project project, Employee employee, Customer customer, Price paid) throws DataWriteException {
        final Order order = new Order();

        try {
            insertOrderPS.setTimestamp(1, Timestamp.valueOf(createdAt));
            insertOrderPS.setInt(2, project.getId());
            insertOrderPS.setInt(3, employee.getId());
            insertOrderPS.setInt(4, OrderStatus.AWAITING.getValue());
            insertOrderPS.executeQuery();

            final int id = insertOrderPS.getGeneratedKeys().getInt(1);
            order.setId(id);
            order.setDate(createdAt);
            order.setStatus(OrderStatus.AWAITING);
            order.setCustomer(customer);
            order.setEmployee(employee);
            order.setOrderLines(orderLines);

            for (OrderLine orderLine : orderLines) {
                insertOrderLinePS.setInt(1, id);
                insertOrderLinePS.setInt(2, orderLine.getProduct().getId());
                insertOrderLinePS.setInt(3, orderLine.getQuantity());
                insertOrderLinePS.execute();
            }

            final Set<OrderInvoice> orderInvoices = new HashSet<>();
            for (OrderInvoice invoice : invoices) {
                insertOrderInvoicePS.setInt(1, id);
                insertOrderInvoicePS.setInt(2, customer.getId());
                insertOrderInvoicePS.setInt(3, paid.getAmount());
                insertOrderInvoicePS.executeQuery();

                final int invoiceId = insertOrderInvoicePS.getGeneratedKeys().getInt(1);
                final OrderInvoice orderInvoice = new OrderInvoice();
                orderInvoice.setId(invoiceId);
                orderInvoice.setProduct(invoice.getProduct());
                orderInvoice.setQuantity(invoice.getQuantity());
                orderInvoices.add(orderInvoice);
            }
            order.setOrderInvoices(orderInvoices);
        } catch(SQLException e) {
            e.printStackTrace();

            throw new DataWriteException("Unable to create order");
        }

        return order;
    }
}
