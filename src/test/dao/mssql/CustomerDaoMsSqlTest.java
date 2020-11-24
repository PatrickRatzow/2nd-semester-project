package test.dao.mssql;

import dao.CustomerDao;
import dao.mssql.CustomerDaoMsSql;
import datasource.mssql.DataSourceMsSql;
import exception.DataAccessException;
import exception.DataWriteException;
import model.Customer;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerDaoMsSqlTest {
    private CustomerDao customerDao = new CustomerDaoMsSql();

    @BeforeAll
    static void setUpAll() throws SQLException {
        DataSourceMsSql.getInstance().startTransaction();
    }

    @Test
    @DisplayName("findById(id) finds the matching customer if it exists")
    void canFindCustomerByExistingId() throws DataAccessException {
        // Arrange
        final Customer customer;
        final int id = 3;

        // Act
        customer = customerDao.findById(id);

        // Assert
        assertNotNull(customer);
    }

    @Test
    @DisplayName("findById(id) throws DataAccessException if customer doesn't exist in database")
    void cantFindCustomerIfIdDoesntExist() {
        // Arrange
        final int id = 1;

        assertThrows(DataAccessException.class, () -> customerDao.findById(id));
    }

    @Test
    @Order(1)
    @DisplayName("findAll() returns expected amount of customers")
    void testCanFindAll() throws DataAccessException {
        // Arrange
        final List<Customer> customers;
        final int expectedSize = 2;

        // Act
        customers = customerDao.findAll();

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
    void testAddCustomerToDatabase() throws DataWriteException {
    	// Arrange
        final String firstName = "Patrick";
        final String lastName = "Jensen";
        final String email = "patrick@ucn.dk";
        final String phoneNo = "23423422";
        final Customer customer;

    	// Act
    	customer = customerDao.create(firstName, lastName, email, phoneNo);
    	
    	// Assert
    	assertNotNull(customer);
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
        DataSourceMsSql.getInstance().rollbackTransaction();
    }
}
