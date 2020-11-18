package persistence.dao.mssql;

import exception.DataAccessException;
import exception.DataWriteException;
import model.ProductCategory;
import persistence.connection.mssql.MsSqlPersistenceConnection;
import persistence.dao.ProductCategoryDao;
import persistence.repository.mssql.dto.ProductCategoryDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MsSqlProductCategoryDao implements ProductCategoryDao {
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

    public MsSqlProductCategoryDao() {
        init();
    }

    private void init() {
        final MsSqlPersistenceConnection con = MsSqlPersistenceConnection.getInstance();

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

    private ProductCategoryDto buildObject(final ResultSet rs) throws SQLException {
        final ProductCategoryDto categoryDto;

        final int id = rs.getInt("productCategoryId");
        final String name = rs.getString("productCategoryName");
        final String desc = rs.getString("productCategoryDesc");
        categoryDto = new ProductCategoryDto(id, name, desc);

        return categoryDto;
    }

    private List<ProductCategoryDto> buildObjects(final ResultSet rs) throws SQLException {
        final List<ProductCategoryDto> categoryDtos = new ArrayList<>();

        while (rs.next()) {
            final ProductCategoryDto categoryDto = buildObject(rs);
            categoryDtos.add(categoryDto);
        }

        return categoryDtos;
    }

    @Override
    public List<ProductCategoryDto> findAll() {
        List<ProductCategoryDto> categories = new ArrayList<>();

        try {
            ResultSet rs = findAllPS.executeQuery();
            categories = buildObjects(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    @Override
    public ProductCategoryDto findById(int id) throws DataAccessException {
        final ProductCategoryDto categoryDto;

        try {
            findByIdPS.setInt(1, id);
            ResultSet rs = findByIdPS.executeQuery();

            if (!rs.next()) {
                throw new DataAccessException("Unable to find any category with this ID");
            }

            categoryDto = buildObject(rs);
        } catch (SQLException e) {
            e.printStackTrace();

            throw new DataAccessException("Unable to find any category with this ID");
        }


        return categoryDto;
    }

    @Override
    public List<ProductCategoryDto> findByName(String name) throws DataAccessException {
        List<ProductCategoryDto> categories = new ArrayList<>();

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

    @Override
    public ProductCategory create(String name, String desc) throws DataWriteException {
        final ProductCategory category;

        try {
            insertPS.setString(1, name);
            insertPS.setString(2, desc);
            insertPS.execute();
            try (ResultSet rs = insertPS.getGeneratedKeys()) {
                if (!rs.next()) {
                    throw new DataWriteException("Unable to create category");
                }

                final ProductCategory temp = new ProductCategory();
                final int insertId = rs.getInt(1);
                temp.setId(insertId);
                temp.setName(name);
                temp.setDesc(desc);
                category = temp;
            }
        } catch (SQLException e) {
            e.printStackTrace();

            throw new DataWriteException("Unable to create category");
        }

        return category;
    }

    @Override
    public void update(int id, String name, String desc) throws DataWriteException {
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
