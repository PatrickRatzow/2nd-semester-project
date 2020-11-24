package test.service.mssql;

import datasource.mssql.DataSourceMsSql;
import exception.DataAccessException;
import model.Order;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.OrderService;
import service.mssql.OrderServiceMsSql;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderServiceMsSqlTest {
    private final OrderService orderService = new OrderServiceMsSql();

    @BeforeAll
    static void setUpAll() throws SQLException {
        DataSourceMsSql.getInstance().startTransaction();
    }

    @Test
    @DisplayName("findById(id) finds an order if it exists in database")
    void canFindOrderByExistingId() throws DataAccessException {
        // Arrange
        final Order order;
        final int id = 1;

        // Act
        order = orderService.findById(id);

        // Assert
        assertNotNull(order);
    }

    @Test
    @DisplayName("findById(id) throws a DataAccessException if it doesn't exist")
    void cantFindOrderIfIdIsntInDatabase(){
        // Arrange
        final int id = 450;

        // Act
        assertThrows(DataAccessException.class, () -> orderService.findById(id));
    }

    @AfterAll
    static void tearDownAll() throws SQLException {
        DataSourceMsSql.getInstance().commitTransaction();
    }
}
