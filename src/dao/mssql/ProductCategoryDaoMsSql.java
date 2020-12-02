package dao.mssql;

import dao.ProductCategoryDao;
import datasource.DBConnection;
import entity.ProductCategory;
import exception.DataAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProductCategoryDaoMsSql implements ProductCategoryDao {
    private static final String FIND_ALL_Q = "SELECT * FROM product_category";
    private PreparedStatement findAllPS;
    private static final String FIND_BY_ID_Q = FIND_ALL_Q + " WHERE id = ?";
    private PreparedStatement findByIdPS;
    private static final String FIND_BY_NAME_Q = FIND_ALL_Q + " WHERE name = ?";
    private PreparedStatement findByNamePS;

    public ProductCategoryDaoMsSql(DBConnection conn) {
        init(conn);
    }

    private void init(DBConnection conn) {
        try {
            findAllPS = conn.prepareStatement(FIND_ALL_Q);
            findByIdPS = conn.prepareStatement(FIND_BY_ID_Q);
            findByNamePS = conn.prepareStatement(FIND_BY_NAME_Q);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private ProductCategory buildObject(final ResultSet rs) throws SQLException {
        ProductCategory category = null;

        final int id = rs.getInt("id");
        final String name = rs.getString("name");
        final String desc = rs.getString("description");
        category = new ProductCategory(id, name, desc);

        return category;
    }

    private List<ProductCategory> buildObjects(final ResultSet rs) throws SQLException {
        final List<ProductCategory> categories = new ArrayList<>();

        while (rs.next()) {
            final ProductCategory category = buildObject(rs);
            categories.add(category);
        }

        return categories;
    }

    @Override
    public ProductCategory findById(int id) throws DataAccessException {
        ProductCategory category = null;

        try {
            findByIdPS.setInt(1, id);
            ResultSet rs = findByIdPS.executeQuery();

            if (rs.next()) {
                category = buildObject(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();

            throw new DataAccessException("Unable to find any category with this ID");
        }


        return category;
    }

    @Override
    public List<ProductCategory> findByName(String name) throws DataAccessException {
        List<ProductCategory> categories = new LinkedList<>();

        try {
            findByNamePS.setString(1, name);
            ResultSet rs = findByNamePS.executeQuery();

            categories = buildObjects(rs);
        } catch (SQLException e) {
            e.printStackTrace();

            throw new DataAccessException("Unable to find any category with the name - " + name);
        }

        return categories;
    }
}
