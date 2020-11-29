package dao.mssql;

import dao.ProductDao;
import datasource.DBConnection;
import entity.Price;
import entity.Product;
import exception.DataAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Product db.
 */
public class ProductDaoMsSql implements ProductDao {
    private static final String FIND_ALL_Q = "SELECT * FROM GetProducts";
    private PreparedStatement findAllPS;
    private static final String FIND_BY_ID_Q = "SELECT * FROM GetProducts WHERE id = ? " +
            "AND GETUTCDATE() BETWEEN price_start_time AND price_end_time " +
            "ORDER BY price_end_time DESC";
    private PreparedStatement findByIdPS;
    private static final String FIND_BY_NAME_Q = "SELECT * FROM GetProducts WHERE name LIKE CONCAT('%', ?, '%') " +
            "GETUTCDATE() BETWEEN price_start_time AND price_end_time" +
            "ORDER BY price_end_time DESC";
    private PreparedStatement findByNamePS;
    private DBConnection connection;

    /**
     * Instantiates a new Product db.
     */
    public ProductDaoMsSql(DBConnection conn) {
        init(conn);
    }

    private void init(DBConnection conn) {
        connection = conn;

        try {
            findByIdPS = conn.prepareStatement(FIND_BY_ID_Q);
            findAllPS = conn.prepareStatement(FIND_ALL_Q);
            findByNamePS = conn.prepareStatement(FIND_BY_NAME_Q);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private Product buildObject(ResultSet rs) throws SQLException {
        final Product product = new Product();

        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setDesc(rs.getString("description"));
        product.setPrice(new Price(rs.getInt("price")));

        return product;
    }

    private List<Product> buildObjects(ResultSet rs) throws SQLException {
        final List<Product> products = new ArrayList<>();

        while (rs.next()) {
            Product product = buildObject(rs);
            products.add(product);
        }

        return products;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();

        try {
            ResultSet rs = findAllPS.executeQuery();
            products = buildObjects(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public Product findById(int id) throws DataAccessException {
        Product product = null;

        try {
            findByIdPS.setInt(1, id);
            ResultSet rs = findByIdPS.executeQuery();

            if (rs.next()) {
                product = buildObject(rs);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Unable to find any product with id " + id);
        }

        return product;
    }

    @Override
    public List<Product> findByIds(List<Integer> ids) throws DataAccessException {
        if (ids.size() == 0) throw new DataAccessException("Empty list passed! You need at least 1 entry");

        List<Product> products = new LinkedList<>();

        try {
            String idParameters = ids.stream().map(x -> "?").collect(Collectors.joining(","));
            String query = "SELECT * FROM GetProducts WHERE id IN (" + idParameters + ") " +
                    "AND GETUTCDATE() BETWEEN price_start_time AND price_end_time " +
                    "ORDER BY price_end_time DESC";
            PreparedStatement stmt = connection.prepareStatement(query);
            int size = ids.size();
            for (int i = 0; i < size; i++) {
                stmt.setInt(i + 1, ids.get(i));
            }
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                products = buildObjects(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to find any product with ids " + ids.toString());
        }

        return products;
    }

    @Override
    public List<Product> findByName(String name) throws DataAccessException {
        List<Product> products = new ArrayList<>();

        try {
            findByNamePS.setString(1, name);
            ResultSet rs = findByNamePS.executeQuery();
            products = buildObjects(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (products.size() == 0) {
            throw new DataAccessException("Unable to find any product with this name");
        }

        return products;
    }
}
