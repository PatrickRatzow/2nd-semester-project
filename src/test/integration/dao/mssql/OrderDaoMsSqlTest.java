package test.integration.dao.mssql;

import dao.OrderDao;
import dao.mssql.OrderDaoMsSql;
import datasource.DBConnection;
import datasource.DBManager;
import entity.Order;
import exception.DataAccessException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderDaoMsSqlTest {
    private static DBConnection connection;
    private static OrderDao dao;

    @BeforeAll
    static void setup() {
        connection = DBManager.getPool().getConnection();
        dao = new OrderDaoMsSql(connection);
    }

    @Test
    void canFindById() throws DataAccessException {
        // Arrange
        Order order = null;

        // Act
        order = dao.findById(1, true);

        // Assert
        assertNotNull(order);
    }

    @AfterAll
    static void teardown() {
        connection.release();
    }
}
