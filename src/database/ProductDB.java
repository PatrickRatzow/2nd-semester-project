package database;

import model.Price;
import model.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Product db.
 */
public class ProductDB implements IProductDB {
    private static final String FIND_ALL_Q = "SELECT * FROM GetProducts";
    private PreparedStatement findAllPS;
    private static final String FIND_BY_ID_Q = FIND_ALL_Q + " WHERE productId = ?";
    private static final String FIND_ALL_BY_CATEGORY_Q = FIND_ALL_Q + " WHERE productCategoryName = ?";
    private PreparedStatement findByIdPS;
    private PreparedStatement findAllByCategoryPS;

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
            findAllByCategoryPS = con.prepareStatement(FIND_ALL_BY_CATEGORY_Q);
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

        }

        return products;
    }

    @Override
    public Product findById(int id) {
        return null;
    }

    @Override
    public List<Product> findByName(String name) {
        return null;
    }

    @Override
    public List<Product> findByCategoryName(String name) {
        return null;
    }

    @Override
    public Product create(Product product) {
        return null;
    }

    @Override
    public void update(Product product) {

    }

    @Override
    public void delete(Product product) {

    }
}
