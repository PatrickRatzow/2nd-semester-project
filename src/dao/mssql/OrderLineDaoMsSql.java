package dao.mssql;

import dao.OrderLineDao;
import datasource.DBConnection;
import entity.OrderLine;
import entity.Product;
import exception.DataAccessException;
import exception.DataWriteException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class OrderLineDaoMsSql implements OrderLineDao {
    private static final String FIND_ALL_BY_ORDER_ID_Q = "SELECT * FROM GetOrderLines WHERE orderId = ?";
    private PreparedStatement findAllByOrderIdPS;
    private static final String INSERT_Q = "";
    private PreparedStatement insertPS;
    private DBConnection connection;

    public OrderLineDaoMsSql(DBConnection conn) {
        init(conn);
    }

    private void init(DBConnection conn) {
        connection = conn;

        try {
            findAllByOrderIdPS = conn.prepareStatement(FIND_ALL_BY_ORDER_ID_Q);
            insertPS = conn.prepareStatement(INSERT_Q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private OrderLine buildObject(ResultSet rs) throws SQLException, DataAccessException {
        final int productId = rs.getInt("productId");
        final int quantity = rs.getInt("quantity");
        final Product product = new ProductDaoMsSql(connection).findById(productId);

        return new OrderLine(product, quantity);
    }

    private List<OrderLine> buildObjects(ResultSet rs) throws SQLException, DataAccessException {
        final List<OrderLine> orderLines = new LinkedList<>();

        while (rs.next()) {
            orderLines.add(buildObject(rs));
        }

        return orderLines;
    }

    @Override
    public List<OrderLine> findByOrderId(int id) throws DataAccessException {
        List<OrderLine> orderLines = new LinkedList<>();

        try {
            findAllByOrderIdPS.setInt(1, id);
            ResultSet rs = findAllByOrderIdPS.executeQuery();

            orderLines = buildObjects(rs);
        } catch (SQLException e) {
            e.printStackTrace();

            throw new DataAccessException("Unable to find any order lines");
        }

        return orderLines;
    }

    @Override
    public OrderLine create(int orderId, int productId, int quantity) throws DataWriteException {
        return null;
    }
}
