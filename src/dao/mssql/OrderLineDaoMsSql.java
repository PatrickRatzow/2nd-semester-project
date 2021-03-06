package dao.mssql;

import dao.OrderLineDao;
import dao.ProductDao;
import datasource.DBConnection;
import datasource.DataAccessException;
import model.Order;
import model.OrderLine;
import model.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

public class OrderLineDaoMsSql implements OrderLineDao {
    private static final String FIND_ALL_BY_ORDER_ID_Q = "SELECT quantity, product_id, display_name" +
            " FROM order_line WHERE order_id = ?";
    private PreparedStatement findAllByOrderIdPS;
    private static final String INSERT_Q = "INSERT INTO order_line(order_id, product_id, quantity, display_name)" +
            " VALUES (?, ?, ?, ?)";
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

    private Entry<Integer, OrderLine> buildObject(ResultSet rs) throws SQLException, DataAccessException {
        final int productId = rs.getInt("product_id");
        final int quantity = rs.getInt("quantity");
        final String displayName = rs.getString("display_name");

        final OrderLine orderLine = new OrderLine();
        orderLine.setQuantity(quantity);
        orderLine.setDisplayName(displayName);

        return new AbstractMap.SimpleEntry<>(productId, orderLine);
    }

    private Map<Integer, OrderLine> buildObjects(ResultSet rs) throws SQLException, DataAccessException {
        final Map<Integer, OrderLine> orderLines = new HashMap<>();

        while (rs.next()) {
            final Entry<Integer, OrderLine> orderLine = buildObject(rs);
            orderLines.put(orderLine.getKey(), orderLine.getValue());
        }

        return orderLines;
    }

    @Override
    public List<OrderLine> findByOrderId(int id) throws DataAccessException {
        List<OrderLine> orderLines = new LinkedList<>();

        try {
            findAllByOrderIdPS.setInt(1, id);
            ResultSet rs = findAllByOrderIdPS.executeQuery();

            final Map<Integer, OrderLine> map = buildObjects(rs);

            ProductDao dao = connection.getDaoFactory().createProductDao();
            List<Product> products = dao.findByIds(new LinkedList<>(map.keySet()));
            for (Product product : products) {
                OrderLine orderLine = map.get(product.getId());
                orderLine.setProduct(product);

                orderLines.add(orderLine);
            }
        } catch (SQLException e) {
            e.printStackTrace();

            throw new DataAccessException("Unable to find any order lines");
        }

        return orderLines;
    }

    @Override
    public void create(Order order, OrderLine orderLine) throws DataAccessException {
        try {
            insertPS.setInt(1, order.getId());
            insertPS.setInt(2, orderLine.getProduct().getId());
            insertPS.setInt(3, orderLine.getQuantity());
            insertPS.setString(4, orderLine.getDisplayName());
            insertPS.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to create order line!");
        }
    }
}
