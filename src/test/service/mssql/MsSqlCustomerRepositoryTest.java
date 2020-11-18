package test.service.mssql;

import exception.DataAccessException;
import model.Customer;
import org.junit.jupiter.api.*;
import persistence.connection.mssql.MsSqlPersistenceConnection;
import persistence.repository.CustomerRepository;
import persistence.repository.mssql.MsSqlCustomerRepository;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MsSqlCustomerRepositoryTest {
    private CustomerRepository customerRepository = new MsSqlCustomerRepository();

    @BeforeAll
    static void setUpAll() throws SQLException {
        MsSqlPersistenceConnection.getInstance().startTransaction();
    }

    @Test
    @DisplayName("findById(id) finds the matching customer if it exists")
    void canFindCustomerByExistingId() throws DataAccessException {
        // Arrange
        final Customer customer;
        final int id = 3;

        // Act
        customer = customerRepository.findById(id);

        // Assert
        assertNotNull(customer);
    }

    @Test
    @DisplayName("findById(id) throws DataAccessException if customer doesn't exist in database")
    void cantFindCustomerIfIdDoesntExist() {
        // Arrange
        final int id = 1;

        assertThrows(DataAccessException.class, () -> customerRepository.findById(id));
    }

    /*
    @Test
    @Order(1)
    @DisplayName("findAll() returns expected amount of customers")
    void testCanFindAll() throws DataAccessException {
        // Arrange
        final List<Customer> customers;
        final int expectedSize = 2;

        // Act
        customers = customerController.findAll();

        // Assert
        assertEquals(customers.size(), expectedSize);
    }

    @Test
    @DisplayName("Can find customer in the database by phone number")
    void testCanFindCustomerByPhoneNo_shouldReturnCustomerFromDatabase() {
        // Arrange

        // Act

        // Assert

        fail();
    }


    @Test
    @DisplayName("Cannot find non-existent customer in database by name")
    void testCanFindCustomerByPhoneNo_shouldReturnNullValueFromDatabase() throws DataWriteException, SQLException {
    	fail();
    }


    @Test
    @DisplayName("Can add a person to the database")
    void testAddPersonToDatabase() throws DataWriteException {
    	// Arrange
        final String firstName = "Patrick";
        final String lastName = "Jensen";
        final String email = "patrick@ucn.dk";
        final String phoneNo = "23423422";
        final Customer customer = new Customer(firstName, lastName, email, phoneNo);
    	final Customer returnCustomer;

    	// Act
    	returnCustomer = customerController.create(customer);
    	
    	// Assert
    	assertNotNull(returnCustomer);
    }


    @Test
    @DisplayName("Can delete a person from the database")
    void testDeletePersonFromDatabase() {
        fail();
    }


    @Test
    @DisplayName("Can find an employee from the database by username and password")
    void testFindEmployeeByUsernameAndPassword() {
        fail();
    }
     */

    @AfterAll
    static void tearDownAll() throws SQLException {
        MsSqlPersistenceConnection.getInstance().rollbackTransaction();
    }
}
