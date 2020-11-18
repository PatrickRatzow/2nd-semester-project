package persistence.dao.mssql;

import exception.DataAccessException;
import exception.DataWriteException;
import model.Order;
import model.OrderStatus;
import persistence.connection.mssql.MsSqlPersistenceConnection;
import persistence.dao.OrderDao;
import persistence.repository.mssql.dto.OrderDto;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MsSqlOrderDao implements OrderDao {
    private static final String FIND_BY_ID_Q = "SELECT * FROM GetOrders WHERE orderId = ?";
    private PreparedStatement findByIdPS;
    private static final String INSERT_Q = "";
    private PreparedStatement insertPS;

    public MsSqlOrderDao() {
        init();
    }

    private void init() {
        final MsSqlPersistenceConnection con = MsSqlPersistenceConnection.getInstance();

        try {
            findByIdPS = con.prepareStatement(FIND_BY_ID_Q);
            insertPS = con.prepareStatement(INSERT_Q, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private OrderDto buildObject(final ResultSet rs) throws SQLException {
        final OrderDto order;

        final int id = rs.getInt("orderId");
        final OrderStatus status = OrderStatus.values()[rs.getInt("orderStatus")];
        // TODO: Fix this
        final LocalDateTime createdAt = LocalDateTime.now();
        final int customerId = rs.getInt("customerId");
        final int employeeId = rs.getInt("employeeId");
        final int orderInvoiceId = rs.getInt("orderInvoiceId");

        order = new OrderDto(id, status, createdAt, customerId, employeeId, orderInvoiceId);
        addOrderLineId(order, rs);

        return order;
    }

    private void addOrderLineId(OrderDto orderDto, ResultSet rs) throws SQLException {
        final int orderLineId = rs.getInt("orderLineId");

        if (orderLineId != 0) {
            orderDto.addOrderLineId(orderLineId);
        }
    }

    private List<OrderDto> buildObjects(ResultSet rs) throws SQLException {
        final Map<Integer, OrderDto> orderDtoMap = new HashMap<>();

        while (rs.next()) {
            int orderId = rs.getInt("orderId");
            OrderDto orderDto = orderDtoMap.get(orderId);

            if (orderDto == null) {
                orderDtoMap.put(orderId, buildObject(rs));
            } else {
                addOrderLineId(orderDto, rs);
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
            List<OrderDto> orderDtos = buildObjects(rs);
            if (orderDtos.size() == 0) {
                throw new DataAccessException("Unable to find any order with id " + id);
            }

            order = orderDtos.get(0);
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
