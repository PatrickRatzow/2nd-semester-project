package test.persistence.mssql.repository;

import exception.DataAccessException;
import model.Order;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.connection.mssql.MsSqlDataSource;
import persistence.repository.OrderRepository;
import persistence.repository.mssql.MsSqlOrderRepository;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MsSqlOrderRepositoryTest {
    private final OrderRepository orderRepository = new MsSqlOrderRepository();

    @BeforeAll
    static void setUpAll() throws SQLException {
        MsSqlDataSource.getInstance().startTransaction();
    }

    @Test
    @DisplayName("findById(id) finds an order if it exists in database")
    void canFindOrderByExistingId() throws DataAccessException {
        // Arrange
        final Order order;
        final int id = 1;

        // Act
        order = orderRepository.findById(id);

        // Assert
        assertNotNull(order);
    }

    @Test
    @DisplayName("findById(id) throws a DataAccessException if it doesn't exist")
    void cantFindOrderIfIdIsntInDatabase(){
        // Arrange
        final int id = 450;

        // Act
        assertThrows(DataAccessException.class, () -> orderRepository.findById(id));
    }

    @AfterAll
    static void tearDownAll() throws SQLException {
        MsSqlDataSource.getInstance().commitTransaction();
    }
}
