package test.service.mssql;

import exception.DataAccessException;
import model.ProductCategory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.connection.mssql.MsSqlPersistenceConnection;
import persistence.repository.ProductCategoryRepository;
import persistence.repository.mssql.MsSqlProductCategoryRepository;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MsSqlProductCategoryRepositoryTest {
    private final ProductCategoryRepository productCategoryRepository = new MsSqlProductCategoryRepository();

    @BeforeAll
    static void setUpAll() throws SQLException {
        MsSqlPersistenceConnection.getInstance().startTransaction();
    }

    @Test
    @DisplayName("findById(id) finds an order if it exists in database")
    void canFindProductCategoryByExistingId() throws DataAccessException {
        // Arrange
        final ProductCategory productCategory;
        final int id = 1;

        // Act
        productCategory = productCategoryRepository.findById(id);

        // Assert
        assertNotNull(productCategory);
    }

    @Test
    @DisplayName("findById(id) throws a DataAccessException if it doesn't exist")
    void cantFindProductCategoryIfIdIsntInDatabase(){
        // Arrange
        final int id = 50;

        // Act
        assertThrows(DataAccessException.class, () -> productCategoryRepository.findById(id));
    }

    @AfterAll
    static void tearDownAll() throws SQLException {
        MsSqlPersistenceConnection.getInstance().commitTransaction();
    }
}
