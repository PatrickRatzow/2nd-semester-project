package test.persistence.mssql.repository;

import exception.DataAccessException;
import model.OrderLine;
import org.junit.jupiter.api.*;
import persistence.connection.mssql.MsSqlDataSource;
import persistence.repository.OrderLineRepository;
import persistence.repository.mssql.MsSqlOrderLineRepository;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MsSqlOrderLineRepositoryTest {
    private final OrderLineRepository orderLineRepository = new MsSqlOrderLineRepository();

    @BeforeAll
    static void setUpAll() throws SQLException {
        MsSqlDataSource.getInstance().startTransaction();
    }

    @Test
    @DisplayName("findById(orderId) finds the matching order lines for that orderId if it exists")
    void canFindOrderLineByExistingId() throws DataAccessException {
        // Arrange
        final List<OrderLine> orderLines;
        final int id = 1;
        final int expectedSize = 3;

        // Act
        orderLines = orderLineRepository.findById(id);

        // Assert
        assertEquals(orderLines.size(), expectedSize);
        orderLines.forEach(Assertions::assertNotNull);
    }

    @Test
    @DisplayName("findById(id) throws a DataAccessException if it doesn't exist")
    void cantFindOrderLineIfIdDoesntExist() throws DataAccessException {
        // Arrange
        final int id = 5000;

        // Act
        assertThrows(DataAccessException.class, () -> orderLineRepository.findById(id));
    }

    @AfterAll
    static void tearDownAll() throws SQLException {
        MsSqlDataSource.getInstance().commitTransaction();
    }
}
