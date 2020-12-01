package test.integration.dao.mssql;

import dao.CustomerDao;
import dao.mssql.CustomerDaoMsSql;
import datasource.DBConnection;
import datasource.DBManager;
import entity.Address;
import entity.Customer;
import exception.DataAccessException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerDaoMsSqlTest {
    private static DBConnection connection;
    private static CustomerDao dao;

    @BeforeAll
    static void setup() {
        connection = DBManager.getPool().getConnection();
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
        Customer customer;

        // Act
        customer = dao.findByPhoneNumber(phoneNumber);

        // Assert
        assertNotNull(customer);
    }

    @Test
    void testCantFindByPhoneNumberIfItDoesntExistInDatabase() throws DataAccessException {
        // Arrange
        String phoneNumber = "99239939";
        Customer customer;

        // Act
        customer = dao.findByPhoneNumber(phoneNumber);

        // Assert
        assertNull(customer);
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
        Customer customer = new Customer(id,"Test", "Test", "test@ucn.dk",
                "11111111", address);

        // It would throw an exception if unable to update
        dao.update(customer);
    }

    @Test
    void testCantUpdateCustomerIfTheyDontExistInDatabase() {
        // Arrange
        int id = 50000;
        Address address = new Address("Testing", 1, "Test", 1);
        Customer customer = new Customer(id,"Test", "Test", "test@ucn.dk",
                "11111111", address);

        // It would throw an exception if unable to update
        assertThrows(DataAccessException.class, () -> dao.update(customer));
    }

    @AfterAll
    static void teardown() {
        connection.release();
    }
}
