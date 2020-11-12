package database;

import model.Price;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Product db.
 */
public class ProductDaoMsSql implements ProductDao {
    private static final String FIND_ALL_Q = "SELECT * FROM GetProducts";
    private PreparedStatement findAllPS;
    private static final String FIND_BY_ID_Q = FIND_ALL_Q + " WHERE productId = ?";
    private PreparedStatement findByIdPS;
    private static final String FIND_BY_NAME_Q = FIND_ALL_Q + " WHERE productName = ?";
    private PreparedStatement findByNamePS;
    private static final String FIND_BY_CATEGORY_NAME_Q = "SELECT * FROM GetProductsWithCategories WHERE productCategoryName = ?";
    private PreparedStatement findByCategoryNamePS;
    private static final String FIND_BY_CATEGORY_ID_Q = "SELECT * FROM GetProductsWithCategories WHERE productCategoryId = ?";
    private PreparedStatement findByCategoryIdPS;
    private static final String INSERT_Q = "{CALL InsertProduct(?, ?, ?, ?, ?, ?)}";
    private CallableStatement insertPC;
    private static final String UPDATE_Q = "{CALL UpdateProduct(?, ?, ?, ?, ?, ?)}";
    private CallableStatement updatePC;

    /**
     * Instantiates a new Product db.
     */
    public ProductDaoMsSql() {
        init();
    }

    private void init() {
        DBConnection con = DBConnection.getInstance();

        try {
            findAllPS = con.prepareStatement(FIND_ALL_Q);
            findByIdPS = con.prepareStatement(FIND_BY_ID_Q);
            findByNamePS = con.prepareStatement(FIND_BY_NAME_Q);
            findByCategoryNamePS = con.prepareStatement(FIND_BY_CATEGORY_NAME_Q);
            findByCategoryIdPS = con.prepareStatement(FIND_BY_CATEGORY_ID_Q);
            insertPC = con.prepareCall(INSERT_Q);
            updatePC = con.prepareCall(UPDATE_Q);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private Product buildObject(ResultSet rs) throws SQLException {
        final Product product = new Product();

        product.setId(rs.getInt("productId"));
        product.setName(rs.getString("productName"));
        product.setDesc(rs.getString("productDesc"));
        product.setPrice(new Price(rs.getInt("productPrice")));

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

    @Override
    public List<Product> findByCategoryName(String name) throws DataAccessException {
        List<Product> products = new ArrayList<>();

        try {
            findByCategoryNamePS.setString(1, name);
            ResultSet rs = findByCategoryNamePS.executeQuery();
            products = buildObjects(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (products.size() == 0) {
            throw new DataAccessException("Couldn't find any products that has that category name");
        }


        return products;
    }

    @Override
    public List<Product> findByCategoryId(int id) {
        List<Product> products = new ArrayList<>();

        try {
            findByCategoryIdPS.setInt(1, id);
            ResultSet rs = findByCategoryIdPS.executeQuery();
            products = buildObjects(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }


    @Override
    public Product create(String name, String description, int categoryId, int supplierId, int price) throws DataWriteException {
        final Product product = new Product();

        try {
            insertPC.setString(1, name);
            insertPC.setString(2, description);
            insertPC.setInt(3, categoryId);
            insertPC.setInt(4, supplierId);
            insertPC.setInt(5, price);
            insertPC.registerOutParameter(6, Types.INTEGER);
            insertPC.execute();

            product.setId(insertPC.getInt(6));
            product.setName(name);
            product.setDesc(description);
            product.setPrice(new Price(price));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataWriteException("Unable to create product");
        }

        return product;
    }

    @Override
    public void update(int id, String name, String description, int categoryId, int supplierId, int price) throws DataWriteException {
        try {
            updatePC.setInt(1, id);
            updatePC.setString(2, name);
            updatePC.setString(3, description);
            updatePC.setInt(4, categoryId);
            updatePC.setInt(5, supplierId);
            updatePC.setInt(6, price);
            int affected = updatePC.executeUpdate();
            if (affected == 0) {
                throw new DataWriteException("Unable to update product");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataWriteException("Unable to update product");
        }
    }
}
