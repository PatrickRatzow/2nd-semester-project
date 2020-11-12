package model.order_line;

import exception.DataAccessException;
import exception.DataWriteException;
import model.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderLineDaoMsSql implements OrderLineDao {
    private static final String FIND_ALL_BY_ORDER_ID_Q = "SELECT * FROM GetOrderLines WHERE orderId = ?";
    private PreparedStatement findAllByOrderId;
    private static final String INSERT_Q = "";
    private PreparedStatement insertPS;

    public OrderLineDaoMsSql() {
        init();
    }

    private void init() {
        final DBConnection con = DBConnection.getInstance();

        try {
            findAllByOrderId = con.prepareStatement(FIND_ALL_BY_ORDER_ID_Q);
            insertPS = con.prepareStatement(INSERT_Q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private OrderLineDto buildObject(ResultSet rs) throws SQLException {
        final OrderLineDto orderLineDto = new OrderLineDto();

        orderLineDto.setOrderId(rs.getInt("orderId"));
        orderLineDto.setProductId(rs.getInt("productId"));
        orderLineDto.setQuantity(rs.getInt("quantity"));

        return orderLineDto;
    }

    private List<OrderLineDto> buildObjects(ResultSet rs) throws SQLException {
        final List<OrderLineDto> orderLineDtos = new ArrayList<>();

        while (rs.next()) {
            orderLineDtos.add(buildObject(rs));
        }

        return orderLineDtos;
    }
    @Override
    public List<OrderLineDto> findAllByOrderId(int id) throws DataAccessException {
        final List<OrderLineDto> orderLineDtos;

        try {
            findAllByOrderId.setInt(1, id);
            ResultSet rs = findAllByOrderId.executeQuery();
            orderLineDtos = buildObjects(rs);
        } catch (SQLException e) {
            e.printStackTrace();

            throw new DataAccessException("Unable to find any order lines");
        }

        return orderLineDtos;
    }

    @Override
    public OrderLineDto create(int orderId, int productId, int quantity) throws DataWriteException {
        return null;
    }
}
