package dao.mssql;

import dao.OrderLineDao;
import datasource.DBConnection;
import entity.Order;
import entity.OrderLine;
import entity.Product;
import exception.DataAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

public class OrderLineDaoMsSql implements OrderLineDao {
    private static final String FIND_ALL_BY_ORDER_ID_Q = "SELECT quantity, product_id FROM order_line WHERE order_id = ?";
    private PreparedStatement findAllByOrderIdPS;
    private static final String INSERT_Q = "INSERT INTO order_line(order_id, product_id, quantity) VALUES (?,?,?)";
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

        final OrderLine orderLine = new OrderLine();
        orderLine.setQuantity(quantity);

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
            List<Product> products = new ProductDaoMsSql(connection).findByIds(new LinkedList<>(map.keySet()));
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
            insertPS.executeQuery();


        } catch(SQLException e) {
            throw new DataAccessException("Unable to create order line!");
        }
    }
}
