package test.integration.dao.mssql;

import dao.ProductCategoryDao;
import dao.mssql.ProductCategoryDaoMsSql;
import datasource.DBConnection;
import datasource.DBManager;
import entity.ProductCategory;
import exception.DataAccessException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
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
    void canFindByName() throws  DataAccessException {
        // Arrange
        List<ProductCategory> categories = new LinkedList<>();

        // Act
        categories = dao.findByName("Nails");
        /// TODO: 12/2/2020 might need to change the product name here

        // Assert
        assertNotEquals(categories.size(), 0);
        // TODO: 12/2/2020 This is definitely retarded 
    }

    
    @Test
    void cannotFindByName() {
        // TODO: 12/2/2020 all of this
    }
    
    
    @AfterAll
    static void teardown() {connection.release();}
}
