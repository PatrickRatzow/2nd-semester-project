package test.integration.dao.mssql;

import dao.CustomerDao;
import dao.mssql.CustomerDaoMsSql;
import datasource.DBConnection;
import datasource.DBManager;
import datasource.DataAccessException;
import model.Address;
import model.Customer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerDaoMsSqlTest {
    private static DBConnection connection;
    private static CustomerDao dao;

    @BeforeAll
    static void setup() {
        connection = DBManager.getInstance().getPool().getConnection();
        dao = new CustomerDaoMsSql(connection);
    }

    @Test
    void testCanFindById() throws DataAccessException {
        // Arrange
        int id = 3;
        Customer customer;

        // Act
        customer = dao.findById(id);

        // Assert
        assertNotNull(customer);
    }

    @Test
    void testCantFindByIdIfItDoesntExistInDatabase() throws DataAccessException {
        // Arrange
        int id = 500000;
        Customer customer;

        // Act
        customer = dao.findById(id);

        // Assert
        assertNull(customer);
    }

    @Test
    void testCanFindByPhoneNumber() throws DataAccessException {
        // Arrange
        String phoneNumber = "44332211";
        List<Customer> customers;

        // Act
        customers = dao.findByPhoneNumberOrEmail(phoneNumber, "");

        // Assert
        assertEquals(customers.size(), 1);
    }

    @Test
    void testCantFindByPhoneNumberIfItDoesntExistInDatabase() throws DataAccessException {
        // Arrange
        String phoneNumber = "99239939";
        List<Customer> customers;

        // Act
        customers = dao.findByPhoneNumberOrEmail(phoneNumber, "");

        // Assert
        assertEquals(customers.size(), 0);
    }

    @Test
    void testCanInsertCustomer() throws DataAccessException {
        // Arrange
        Address address = new Address("Sofiendalsvej", 60, "Aalborg SV", 9200);
        Customer customer = new Customer("Anders", "Andersen", "anders@ucn.dk",
                "45454545", address);
        Customer returnCustomer;

        // Act
        returnCustomer = dao.create(customer);

        // Assert
        assertNotNull(returnCustomer);
    }

    @Test
    void testCanUpdateExistingCustomer() throws DataAccessException {
        // Arrange
        int id = 3;
        Address address = new Address("Testing", 1, "Test", 1);
        Customer customer = new Customer(id, "Test", "Test", "test@ucn.dk",
                "11111111", address);
        Customer returnCustomer;

        // Act
        dao.update(customer);
        returnCustomer = dao.findById(id);

        // Assert
        assertEquals(returnCustomer.getEmail(), customer.getEmail());
    }

    @AfterAll
    static void teardown() {
        connection.release();
    }
}
