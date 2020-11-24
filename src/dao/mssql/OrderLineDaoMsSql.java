package dao.mssql;

import dao.OrderLineDao;
import datasource.mssql.DataSourceMsSql;
import dto.OrderLineDto;
import exception.DataAccessException;
import exception.DataWriteException;
import model.OrderLine;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderLineDaoMsSql implements OrderLineDao {
    private static final String FIND_ALL_BY_ORDER_ID_Q = "SELECT * FROM GetOrderLines WHERE orderId = ?";
    private static final String INSERT_Q = "";
    private PreparedStatement insertPS;

    public OrderLineDaoMsSql() {
        init();
    }

    private void init() {
        final DataSourceMsSql con = DataSourceMsSql.getInstance();

        try {
            insertPS = con.prepareStatement(INSERT_Q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private OrderLineDto buildObject(ResultSet rs) throws SQLException {
        final OrderLineDto orderLineDto;

        final int orderId = rs.getInt("orderId");
        final int productId = rs.getInt("productId");
        final int quantity = rs.getInt("quantity");
        orderLineDto = new OrderLineDto(orderId, productId, quantity);

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
    public List<OrderLineDto> findByOrderId(int id) throws DataAccessException {
        final List<OrderLineDto> orderLineDtos;

        try {
            PreparedStatement findAllByOrderId = DataSourceMsSql.getInstance().prepareStatement(FIND_ALL_BY_ORDER_ID_Q);
            findAllByOrderId.setInt(1, id);
            ResultSet rs = findAllByOrderId.executeQuery();

            orderLineDtos = buildObjects(rs);
            if (orderLineDtos.size() == 0) {
                throw new DataAccessException("Unable to find any order lines with orderId " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();

            throw new DataAccessException("Unable to find any order lines");
        }

        return orderLineDtos;
    }

    @Override
    public List<List<OrderLineDto>> findByOrderId(List<Integer> ids) throws DataAccessException {
        final List<List<OrderLineDto>> orderLineDtos = new ArrayList<>();

        for (int id : ids) {
            orderLineDtos.add(findByOrderId(id));
        }

        return orderLineDtos;
    }

    @Override
    public OrderLine create(int orderId, int productId, int quantity) throws DataWriteException {
        return null;
    }
}
