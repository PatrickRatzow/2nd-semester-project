package database;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDB implements IProductCategoryDB {
    private static final String FIND_ALL_Q = "SELECT * FROM GetCategories";
    private PreparedStatement findAllPS;
    private static final String FIND_BY_ID_Q = FIND_ALL_Q + " WHERE productCategoryId = ?";
    private PreparedStatement findByIdPS;
    private static final String FIND_BY_NAME_Q = FIND_ALL_Q + " WHERE productCategoryName = ?";
    private PreparedStatement findByNamePS;
    private static final String INSERT_Q = "INSERT INTO products_categories(name, description) VALUES (?, ?)";
    private PreparedStatement insertPS;
    private static final String UPDATE_Q = "UPDATE products_categories SET name = ?, description = ? WHERE id = ?";
    private PreparedStatement updatePS;
    private static final String DELETE_Q = "DELETE FROM products_categories WHERE id = ?";
    private PreparedStatement deletePS;

    public ProductCategoryDB() {
        init();
    }

    private void init() {
        DBConnection con = DBConnection.getInstance();

        try {
            findAllPS = con.prepareStatement(FIND_ALL_Q);
            findByIdPS = con.prepareStatement(FIND_BY_ID_Q);
            findByNamePS = con.prepareStatement(FIND_BY_NAME_Q);
            insertPS = con.prepareStatement(INSERT_Q, Statement.RETURN_GENERATED_KEYS);
            updatePS = con.prepareStatement(UPDATE_Q);
            deletePS = con.prepareStatement(DELETE_Q);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private ProductCategory buildObject(ResultSet rs) throws SQLException {
        ProductCategory category = new ProductCategory();

        category.setId(rs.getInt("productCategoryId"));
        category.setName(rs.getString("productCategoryName"));
        category.setDesc(rs.getString("productCategoryDesc"));

        return category;
    }

    private List<ProductCategory> buildObjects(ResultSet rs) throws SQLException {
        List<ProductCategory> categories = new ArrayList<>();

        while (rs.next()) {
            ProductCategory category = buildObject(rs);
            categories.add(category);
        }

        return categories;
    }

    @Override
    public List<ProductCategory> findAll() {
        List<ProductCategory> categories = new ArrayList<>();

        try {
            ResultSet rs = findAllPS.executeQuery();
            categories = buildObjects(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    @Override
    public ProductCategory findById(int id) throws DataAccessException {
        ProductCategory category = null;

        try {
            findByIdPS.setInt(1, id);
            ResultSet rs = findByIdPS.executeQuery();

            if (!rs.next()) {
                throw new DataAccessException("Unable to find any category with this ID");
            }

            category = buildObject(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (category == null) {
            throw new DataAccessException("Unable to find any category with this ID");
        }

        return category;
    }

    @Override
    public List<ProductCategory> findByName(String name) throws DataAccessException {
        List<ProductCategory> categories = new ArrayList<>();

        try {
            findByNamePS.setString(1, name);
            ResultSet rs = findByNamePS.executeQuery();
            categories = buildObjects(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (categories.size() == 0) {
            throw new DataAccessException("Unable to find any category with this name");
        }

        return categories;
    }

    @Override
    public ProductCategory create(String name, String desc) throws DataWriteException {
        ProductCategory category = null;

        try {
            insertPS.setString(1, name);
            insertPS.setString(2, desc);
            insertPS.execute();
            try (ResultSet rs = insertPS.getGeneratedKeys()) {
                if (!rs.next()) {
                    throw new DataWriteException("Unable to create category");
                }

                ProductCategory temp = new ProductCategory();
                temp.setId((int) rs.getLong(1));
                temp.setName(name);
                temp.setDesc(desc);
                category = temp;
            }
        } catch (SQLException e) {
            throw new DataWriteException("Unable to create category");
        }

        return category;
    }

    @Override
    public void update(int id, String name, String desc) throws DataWriteException {
        ProductCategory category = new ProductCategory();

        try {
            updatePS.setString(1, name);
            updatePS.setString(2, desc);
            updatePS.setInt(3, id);
            int affected = updatePS.executeUpdate();
            if (affected == 0) {
                throw new DataWriteException("Unable to update category");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataWriteException("Unable to update category");
        }
    }

    @Override
    public void delete(int id) throws DataWriteException {
        try {
            deletePS.setInt(1, id);
            int affected = deletePS.executeUpdate();
            if (affected == 0) {
                throw new DataWriteException("Unable to delete category as it doesn't seem to exist");
            }
        } catch (SQLException e) {
            throw new DataWriteException("Unable to delete category");
        }
    }
}
