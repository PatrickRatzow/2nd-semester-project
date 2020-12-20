package test.integration.dao.mssql;

import dao.OrderDao;
import datasource.DBConnection;
import datasource.DBManager;
import datasource.DataAccessException;
import model.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class OrderDaoMsSqlTest {
    private static DBConnection connection;
    private static OrderDao dao;

    @BeforeAll
    static void setup() {
        connection = DBManager.getInstance().getPool().getConnection();
        dao = connection.getDaoFactory().createOrderDao();
        try {
            connection.startTransaction();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void testCanFindById() throws DataAccessException {
        // Arrange
        Order order;

        // Act
        order = dao.findById(1, true);

        // Assert
        assertNotNull(order);
    }

    @Test
    void testCannotFindById() throws DataAccessException {
        // Arrange
        Order order;

        // Act
        order = dao.findById(1312312, true);

        // Assert
        assertNull(order);
    }


    @Test
    void testCreateOrderWithValidInformation() throws DataAccessException {
        // Arrange
        Order order = new Order();
        order.setDate(LocalDateTime.now());
        order.setDelivered(false);
        Product product = new Product(1, "Lille tagsten", "", new Price(250000));
        order.addOrderLine(new OrderLine(product, 15, "Test"));
        Project project = new Project();
        project.setId(3);
        Order returnOrder;

        // Act
        returnOrder = dao.create(order, project);

        // Assert
        assertNotNull(returnOrder);
    }

    @Test
    void testFailsToCreateOrderWithInvalidInformation() {
        // Arrange
        Order order = new Order();
        order.setDate(LocalDateTime.now());
        order.setDelivered(false);
        Product product = new Product(4, "PP", "", new Price(2242));
        order.addOrderLine(new OrderLine(product, 3, "Test"));
        Project project = new Project();
        project.setId(50000);

        // Assert + Act
        assertThrows(DataAccessException.class, () -> dao.create(order, project));
    }

    @AfterAll
    static void teardown() {
        try {
            connection.rollbackTransaction();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        
        connection.release();
    }
}
