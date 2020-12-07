package test.integration.dao.mssql;

import dao.mssql.ProductCategoryDaoMsSql;
import datasource.DBConnection;
import datasource.DBManager;
import exception.DataAccessException;
import model.ProductCategory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductCategoryDaoMsSqlTest {
    private static DBConnection connection;
    private static ProductCategoryDaoMsSql dao;

    @BeforeAll
    static void setup() {
        connection = DBManager.getPool().getConnection();
        dao = new ProductCategoryDaoMsSql(connection);
    }

    @Test
    void canFindById() throws DataAccessException {
        // Arrange
        ProductCategory productCategory = null;

        // Act
        productCategory = dao.findById(1);

        // Assert
        assertNotNull(productCategory);
    }


    @Test
    void cannotFindById() throws DataAccessException {
        // Arrange
        ProductCategory productCategory = null;

        // Act
        productCategory = dao.findById(9999);

        // Assert
        assertNull(productCategory);
    }


    @Test
    void canFindByName() throws DataAccessException {
        // Arrange
        List<ProductCategory> categories;

        // Act
        categories = dao.findByName("Tagsten");

        // Assert
        assertEquals(categories.size(), 1);
    }


    @Test
    void cannotFindByName() throws DataAccessException {
        // Arrange
        List<ProductCategory> categories;

        // Act
        categories = dao.findByName("XDXDXDXDXDXD");

        // Assert
        assertEquals(categories.size(), 0);
    }


    @AfterAll
    static void teardown() {
        connection.release();
    }
}
