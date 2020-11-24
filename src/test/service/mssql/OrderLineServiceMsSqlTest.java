package test.service.mssql;

import datasource.mssql.DataSourceMsSql;
import exception.DataAccessException;
import model.OrderLine;
import org.junit.jupiter.api.*;
import service.OrderLineService;
import service.mssql.OrderLineServiceMsSql;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderLineServiceMsSqlTest {
    private final OrderLineService orderLineService = new OrderLineServiceMsSql();

    @BeforeAll
    static void setUpAll() throws SQLException {
        DataSourceMsSql.getInstance().startTransaction();
    }

    @Test
    @DisplayName("findById(orderId) finds the matching order lines for that orderId if it exists")
    void canFindOrderLineByExistingId() throws DataAccessException {
        // Arrange
        final List<OrderLine> orderLines;
        final int id = 1;
        final int expectedSize = 3;

        // Act
        orderLines = orderLineService.findById(id);

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
        assertThrows(DataAccessException.class, () -> orderLineService.findById(id));
    }

    @AfterAll
    static void tearDownAll() throws SQLException {
        DataSourceMsSql.getInstance().commitTransaction();
    }
}
