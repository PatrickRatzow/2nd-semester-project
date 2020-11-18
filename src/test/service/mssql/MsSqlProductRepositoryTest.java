package test.service.mssql;

import exception.DataAccessException;
import model.Product;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.connection.mssql.MsSqlPersistenceConnection;
import persistence.repository.ProductRepository;
import persistence.repository.mssql.MsSqlProductRepository;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MsSqlProductRepositoryTest {
    private final ProductRepository productRepository = new MsSqlProductRepository();

    @BeforeAll
    static void setUpAll() throws SQLException {
        MsSqlPersistenceConnection.getInstance().startTransaction();
    }

    @Test
    @DisplayName("findById(id) finds a product if it exists in database")
    void canFindProductByExistingId() throws DataAccessException {
        // Arrange
        final Product product;
        final int id = 1;

        // Act
        product = productRepository.findById(id);

        // Assert
        assertNotNull(product);
    }

    @Test
    @DisplayName("findById(id) throws a DataAccessException if it doesn't exist")
    void cantFindProductIfIdIsntInDatabase(){
        // Arrange
        final int id = 800;

        // Act
        assertThrows(DataAccessException.class, () -> productRepository.findById(id));
    }

    @AfterAll
    static void tearDownAll() throws SQLException {
        MsSqlPersistenceConnection.getInstance().commitTransaction();
    }
}
