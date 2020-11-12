package controller;

import database.DBConnection;
import database.DataAccessException;
import database.DataWriteException;
import model.Customer;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerControllerTest {
    private CustomerController customerController = new CustomerController();

    @BeforeAll
    static void setUpAll() throws SQLException {
        DBConnection.getInstance().startTransaction();
    }

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

    @AfterAll
    static void tearDownAll() throws SQLException {
        DBConnection.getInstance().rollbackTransaction();
    }
}
