package controller;

import database.DBConnection;
import database.DataAccessException;
import model.Customer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CustomerControllerTest {
    private CustomerController customerController = new CustomerController();

    @BeforeAll
    static void setUpAll() throws SQLException {
        DBConnection.getInstance().startTransaction();
    }

    @Test
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
    void testCanFindCustomerByPhoneNo_shouldReturnNullValueFromDatabase() {
        fail();
    }


    @Test
    @DisplayName("Can add a person to the database")
    void testAddPersonToDatabase() {
        fail();
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
