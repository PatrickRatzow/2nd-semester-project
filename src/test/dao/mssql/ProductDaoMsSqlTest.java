package test.dao.mssql;

import dao.ProductDao;
import dao.mssql.ProductDaoMsSql;
import datasource.mssql.DataSourceMsSql;
import exception.DataAccessException;
import model.Product;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductDaoMsSqlTest {
    private final ProductDao productDao = new ProductDaoMsSql();

    @BeforeAll
    static void setUpAll() throws SQLException {
        DataSourceMsSql.getInstance().startTransaction();
    }

    @Test
    @DisplayName("findById(id) finds a product if it exists in database")
    void canFindProductByExistingId() throws DataAccessException {
        // Arrange
        final Product product;
        final int id = 1;

        // Act
        product = productDao.findById(id);

        // Assert
        assertNotNull(product);
    }

    @Test
    @DisplayName("findById(id) throws a DataAccessException if it doesn't exist")
    void cantFindProductIfIdIsntInDatabase(){
        // Arrange
        final int id = 800;

        // Act
        assertThrows(DataAccessException.class, () -> productDao.findById(id));
    }

    @AfterAll
    static void tearDownAll() throws SQLException {
        DataSourceMsSql.getInstance().commitTransaction();
    }
}
