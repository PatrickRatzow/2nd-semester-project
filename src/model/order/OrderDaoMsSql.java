package model.order;

import exception.DataAccessException;
import exception.DataWriteException;
import model.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        OrderDto order = new OrderDto();

        try {
            order.setId(rs.getInt("orderId"));
            order.setStatus(OrderStatus.values()[(rs.getInt("orderStatus")) - 1]);
            // TODO: Fix this
            order.setCreatedAt(LocalDateTime.now());
            order.setCustomerId(rs.getInt("customerId"));
            order.setEmployeeId(rs.getInt("employeeId"));
            order.setOrderId(rs.getInt("orderInvoiceId"));

            order = mergeObject(order, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }

    private OrderDto mergeObject(OrderDto orderDto, ResultSet rs) throws SQLException {
        int orderLineId = rs.getInt("orderLineId");
        int orderInvoiceId = rs.getInt("orderInvoiceId");

        if (orderLineId != 0) {
            orderDto.addOrderLineId(orderLineId);
        }

        return orderDto;
    }

    private List<OrderDto> buildObjects(ResultSet rs) throws SQLException {
        final Map<Integer, OrderDto> orderDtoMap = new HashMap<>();

        while (rs.next()) {
            int orderId = rs.getInt("orderId");
            OrderDto orderDto = orderDtoMap.get(orderId);

            if (orderDto == null) {
                orderDtoMap.put(orderId, buildObject(rs));
            } else {
                orderDtoMap.put(orderId, mergeObject(orderDto, rs));
            }
        }

        return new ArrayList<>(orderDtoMap.values());
    }

    @Override
    public OrderDto findById(int id) throws DataAccessException {
        final OrderDto order;

        try {
            findByIdPS.setInt(1, id);
            ResultSet rs = findByIdPS.executeQuery();
            order = buildObjects(rs).get(0);
        } catch (SQLException e) {
            e.printStackTrace();

            throw new DataAccessException("Unable to find any order with this ID");
        }

        if (order == null) {
            throw new DataAccessException("Unable to find any order with id " + id);
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
