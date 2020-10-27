package database;

import model.DataAccessException;
import model.Price;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Product db.
 */
public class ProductDB implements IProductDB {
    private static final String FIND_ALL_Q = "SELECT * FROM GetProducts";
    private PreparedStatement findAllPS;
    private static final String FIND_BY_ID_Q = FIND_ALL_Q + " WHERE productId = ?";
    private PreparedStatement findByIdPS;
    private static final String FIND_BY_NAME_Q = FIND_ALL_Q + " WHERE productName = ?";
    private static final String FIND_BY_CATEGORY_Q = FIND_ALL_Q + " WHERE productCategoryName = ?";
    private static final String INSERT_Q = "{CALL InsertProduct(?, ?, ?, ?, ?)}";
    private static final String UPDATE_Q = "{CALL UpdateProduct(?, ?, ?, ?, ?)}";
    private PreparedStatement findByNamePS;
    private PreparedStatement findByCategoryPS;
    private CallableStatement insertPC;
    private CallableStatement updatePC;

    /**
     * Instantiates a new Product db.
     */
    public ProductDB() {
        init();
    }

    private void init() {
        DBConnection con = DBConnection.getInstance();

        try {
            findAllPS = con.prepareStatement(FIND_ALL_Q);
            findByIdPS = con.prepareStatement(FIND_BY_ID_Q);
            findByNamePS = con.prepareStatement(FIND_BY_NAME_Q);
            findByCategoryPS = con.prepareStatement(FIND_BY_CATEGORY_Q);
            insertPC = con.prepareCall(INSERT_Q);
            updatePC = con.prepareCall(UPDATE_Q);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private Product buildObject(ResultSet rs) throws SQLException {
        Product product = new Product();

        product.setId(rs.getInt("productId"));
        product.setName(rs.getString("productName"));
        product.setDesc(rs.getString("productDesc"));
        product.setPrice(new Price(rs.getInt("productPrice")));

        return product;
    }

    private List<Product> buildObjects(ResultSet rs) throws SQLException {
        List<Product> products = new ArrayList<>();

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

            if (!rs.next()) {
                throw new DataAccessException("Unable to find any product with this ID");
            }

            product = buildObject(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (product == null) {
            throw new DataAccessException("Unable to find any product with this ID");
        }

        return product;
    }

    @Override
    public List<Product> findByName(String name) {
        List<Product> products = new ArrayList<>();

        try {
            findByNamePS.setString(1, name);
            ResultSet rs = findByNamePS.executeQuery();
            products = buildObjects(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> findByCategoryName(String name) {
        List<Product> products = new ArrayList<>();

        try {
            findByCategoryPS.setString(1, name);
            ResultSet rs = findByCategoryPS.executeQuery();
            products = buildObjects(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public Product create(String name, String description, String categoryName, int price) {
        Product product = null;

        try {
            insertPC.setString(1, name);
            insertPC.setString(2, description);
            insertPC.setString(3, categoryName);
            insertPC.setInt(4, price);
            insertPC.registerOutParameter(5, Types.INTEGER);
            insertPC.execute();

            Product temp = new Product();
            temp.setId(insertPC.getInt(5));
            temp.setName(name);
            temp.setDesc(description);
            temp.setPrice(new Price(price));
            temp.setCategory(categoryName);

            product = temp;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    @Override
    public void update(int id, String name, String description, String categoryName, int price) {
        try {
            updatePC.setInt(1, id);
            updatePC.setString(2, name);
            updatePC.setString(3, description);
            updatePC.setString(4, categoryName);
            updatePC.setInt(5, price);
            updatePC.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
