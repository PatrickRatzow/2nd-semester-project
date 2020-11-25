package test.dao.mssql;

import dao.EmployeeDao;
import dao.mssql.EmployeeDaoMsSql;
import datasource.mssql.DataSourceMsSql;
import exception.DataAccessException;
import exception.DataWriteException;
import entity.Employee;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeDaoMsSqlTest {
    private EmployeeDao employeeDao = new EmployeeDaoMsSql();

    @BeforeAll
    static void setUpAll() throws SQLException {
        DataSourceMsSql.getInstance().startTransaction();
    }

    @Test
    @DisplayName("findById(id) finds the matching employee if it exists")
    void canFindEmployeeByExistingId() throws DataAccessException {
        // Arrange
        final Employee employee;
        final int id = 1;

        // Act
        employee = employeeDao.findById(id);

        // Assert
        assertNotNull(employee);
    }

    @Test
    @DisplayName("findById(id) throws DataAccessException if employee doesn't exist in database")
    void cantFindEmployeeIfIdDoesntExist() {
        // Arrange
        final int id = 3;

        assertThrows(DataAccessException.class, () -> employeeDao.findById(id));
    }

    @Test
    // Enforce order as methods below us inserts into database, and we need to do our expected finds first
    @Order(1)
    @DisplayName("findAll() works")
    void canFindAllEmployeesInDatabase() throws DataAccessException {
        final List<Employee> employees;
        final int expectedSize = 2;

        employees = employeeDao.findAll();

        assertEquals(employees.size(), expectedSize);
    }

    @Test
    @Order(2)
    @DisplayName("create() works")
    void canCreateEmployee() throws DataWriteException {
        final String firstName = "Allan";
        final String lastName = "Jensen";
        // Surprisingly a valid email
        final String email = "allanjensen@ucn@example.com";
        final String phoneNo = "55555555";
        final String username = "allanjensen";
        final byte[] paswordHash = new byte[80];
        final Employee employee;

        // Act
        employee = employeeDao.create(firstName, lastName, email, phoneNo, username, paswordHash);

        // Assert
        assertNotNull(employee);
    }

    @Test
    @Order(3)
    @DisplayName("create() throws DataWriteException if there's already an employee with that username")
    void cantCreateEmployee() {
        final String firstName = "Cas";
        final String lastName = "Cas";
        final String email = "cas@example.com";
        final String phoneNo = "45454545";
        final String username = "cas789";
        final byte[] passwordHash = new byte[80];

        // Act
        assertThrows(DataWriteException.class, () ->
                employeeDao.create(firstName, lastName, email, phoneNo, username, passwordHash));
    }

    @Test
    @DisplayName("findByUsername() works if user exists & password is correct")
    void canFindByUsernameAndPassword() throws DataAccessException {
        final String username = "Cas789";
        final Employee employee;

        employee = employeeDao.findByUsername(username);

        assertNotNull(employee);
    }

    @Test
    @DisplayName("findByUsername() throws a DataAccessException if user doesn't exist")
    void cantFindByUsernameAndPasswordIfUserDoesNotExist() {
        final String username = "Cas789";

        assertThrows(DataAccessException.class, () -> employeeDao.findByUsername(username));
    }

    @Test
    @DisplayName("update() works if id exists in db")
    void canUpdateExisting() throws DataWriteException, DataAccessException {
        final int id = 1;
        final String firstName = "Cas";
        final String lastName = "Cas";
        final String email = "cas@example.com";
        final String phoneNo = "45454545";
        final String username = "cas789";
        final byte[] passwordHash = new byte[80];

        // Act
        employeeDao.update(id, firstName, lastName, email, phoneNo, username, passwordHash);

        // It would throw an exception if it failed
    }

    @Test
    @DisplayName("update() throws DataWriteException if id doesnt exist")
    void cantUpdateNonExisting() {
        final int id = 50203423;
        final String firstName = "Cas";
        final String lastName = "Cas";
        final String email = "cas@example.com";
        final String phoneNo = "45454545";
        final String username = "cas789";
        final byte[] passwordHash = new byte[80];

        // Act + assert
        assertThrows(DataWriteException.class, () ->
                employeeDao.update(id, firstName, lastName, email, phoneNo, username, passwordHash));
    }


    @AfterAll
    static void tearDownAll() throws SQLException {
        DataSourceMsSql.getInstance().rollbackTransaction();
    }
}