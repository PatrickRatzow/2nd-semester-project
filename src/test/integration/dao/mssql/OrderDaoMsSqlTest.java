package test.integration.dao.mssql;

import dao.OrderDao;
import dao.mssql.OrderDaoMsSql;
import datasource.DBConnection;
import datasource.DBManager;
import exception.DataAccessException;
import model.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void cannotFindById() throws DataAccessException {
        // Arrange
        Order order = null;

        // Act
        order = dao.findById(1312312, true);

        //Assert
        assertNull(order);
    }


    @Test
    void testCreateOrderWithValidInformation() throws DataAccessException {
        // Arrange
        Order order = new Order();
        order.setEmployee(new Employee(1, "Allan", "Jensen"));
        order.setCustomer(new Customer(3, "First", "Last", "email@email.xd",
                "4545454545", new Address("Sofiendalsvej", 60,
                "Aalborg SV", 9200)));
        order.setDate(LocalDateTime.now());
        order.setDelivered(false);
        Product product = new Product(1, "Lille tagsten", "", new Price(250000));
        order.addOrderLine(new OrderLine(product, 15, "Test"));
        Project project = new Project();
        project.setId(1);
        Order returnOrder;

        // Act
        returnOrder = dao.create(order, project);

        // Assert
        assertNotNull(returnOrder);
    }

    @Test
    void testFailsToCreateOrderWithInvalidInformation() throws DataAccessException {
        // Arrange
        Order order = new Order();
        order.setEmployee(new Employee(5000, "Allan", "Jensen"));
        order.setCustomer(new Customer(1, "", "", "", "",
                new Address("", 54, "", 9000)));
        order.setDate(LocalDateTime.now());
        order.setDelivered(false);
        Product product = new Product(1, "PP", "", new Price(2242));
        order.addOrderLine(new OrderLine(product, 3, "Test"));
        Project project = new Project();
        project.setId(1);

        // Assert + Act
        assertThrows(DataAccessException.class, () -> dao.create(order, project));

    }

    @AfterAll
    static void teardown() {
        connection.release();
    }
}
