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
import java.util.List;

/**
 * The type Product db.
 */
public class ProductDaoMsSql implements ProductDao {
    private static final String FIND_ALL_Q = "SELECT * FROM GetProducts";
    private PreparedStatement findAllPS;
    private static final String FIND_BY_ID_Q = "SELECT TOP 1 * FROM GetProducts WHERE id = ? " +
            "ORDER BY price_end_time DESC";
    private PreparedStatement findByIdPS;
    private static final String FIND_BY_NAME_Q = "SELECT TOP 1 * FROM GetProducts WHERE name LIKE CONCAT('%', ?, '%') " +
            "ORDER BY price_end_time DESC";
    private PreparedStatement findByNamePS;

    /**
     * Instantiates a new Product db.
     */
    public ProductDaoMsSql(DBConnection conn) {
        init(conn);
    }

    private void init(DBConnection conn) {
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
