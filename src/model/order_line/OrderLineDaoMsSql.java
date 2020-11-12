package model.order_line;

import exception.DataAccessException;
import exception.DataWriteException;
import model.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderLineDaoMsSql implements OrderLineDao {
    private static final String FIND_ALL_BY_ORDER_ID_Q = "SELECT * FROM GetOrders WHERE projectId = ?";
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

    @Override
    public List<OrderLineDto> findAllByOrderId(int id) throws DataAccessException {
        return null;
    }

    @Override
    public OrderLineDto create(int orderId, int productId, int quantity) throws DataWriteException {
        return null;
    }
}
