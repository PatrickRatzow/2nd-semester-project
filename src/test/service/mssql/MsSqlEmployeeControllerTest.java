package test.service.mssql;

import exception.DataAccessException;
import model.Employee;
import org.junit.jupiter.api.*;
import persistence.connection.mssql.MsSqlPersistenceConnection;
import persistence.repository.EmployeeRepository;
import persistence.repository.mssql.MsSqlEmployeeRepository;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MsSqlEmployeeControllerTest {
    private EmployeeRepository employeeRepository = new MsSqlEmployeeRepository();

    @BeforeAll
    static void setUpAll() throws SQLException {
        MsSqlPersistenceConnection.getInstance().startTransaction();
    }

    @Test
    @DisplayName("findById(id) finds the matching employee if it exists")
    void canFindEmployeeByExistingId() throws DataAccessException {
        // Arrange
        final Employee employee;
        final int id = 1;

        // Act
        employee = employeeRepository.findById(id);

        // Assert
        assertNotNull(employee);
    }

    @Test
    @DisplayName("findById(id) throws DataAccessException if employee doesn't exist in database")
    void cantFindEmployeeIfIdDoesntExist() {
        // Arrange
        final int id = 3;

        assertThrows(DataAccessException.class, () -> employeeRepository.findById(id));
    }

    /*
    @Test
    // Enforce order as methods below us inserts into database, and we need to do our expected finds first
    @Order(1)
    @DisplayName("findAll() works")
    void canFindAllEmployeesInDatabase() throws DataAccessException {
        final List<Employee> employees;
        final int expectedSize = 2;

        employees = employeeController.findAll();

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
        final String password = "hunter2";
        final Employee employee = new Employee(firstName, lastName, email, phoneNo, username, password);
        final Employee returnEmployee;

        // Act
        returnEmployee = employeeController.create(employee);

        // Assert
        assertNotNull(returnEmployee);
    }

    @Test
    @Order(2)
    @DisplayName("create() throws IllegalArgumentException if there's already an employee with that username")
    void cantCreateEmployee() {
        final String firstName = "Cas";
        final String lastName = "Cas";
        final String email = "cas@example.com";
        final String phoneNo = "45454545";
        final String username = "cas789";
        final String password = "hunter2";
        final Employee employee = new Employee(firstName, lastName, email, phoneNo, username, password);

        // Act
        assertThrows(IllegalArgumentException.class, () -> employeeController.create(employee));
    }

    @Test
    @DisplayName("create() throws IllegalArgumentException if using an invalid email")
    void cantCreateIfInvalidEmail() {
        final String firstName = "Cas";
        final String lastName = "Cas";
        final String email = "#@%^%#$@#$@#.com";
        final String phoneNo = "45454545";
        final String username = "cas7891";
        final String password = "hunter2";
        final Employee employee = new Employee(firstName, lastName, email, phoneNo, username, password);

        // Act
        assertThrows(IllegalArgumentException.class, () -> employeeController.create(employee));
    }

    @Test
    @DisplayName("findByUsernameAndPassword() works if user exists & password is correct")
    void canFindByUsernameAndPassword() throws DataAccessException, WrongPasswordException {
        final String username = "Cas789";
        final String password = "hunter2";
        final Employee employee;

        employee = employeeController.findByUsernameAndPassword(username, password);

        assertTrue(employee instanceof Employee);
    }

    @Test
    @DisplayName("findByUsernameAndPassword() throws a WrongPasswordException if user exists, but password is incorrect")
    void cantFindByUsernameAndPasswordIfPasswordWrong() {
        final String username = "Cas789";
        final String password = "hunter3";

        assertThrows(WrongPasswordException.class, () -> employeeController.findByUsernameAndPassword(username, password));
    }

    @Test
    @DisplayName("findByUsernameAndPassword() throws a DataAccessException if user doesn't exist")
    void cantFindByUsernameAndPasswordIfUserDoesNotExist() {
        final String username = "Cas78910";
        final String password = "hunter2";

        assertThrows(DataAccessException.class, () -> employeeController.findByUsernameAndPassword(username, password));
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
        final String password = "hunter2";
        final Employee employee = new Employee(firstName, lastName, email, phoneNo, username, password);
        employee.setId(id);

        // Act
        employeeController.update(employee);

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
        final String password = "hunter2";
        final Employee employee = new Employee(firstName, lastName, email, phoneNo, username, password);
        employee.setId(id);

        // Act + assert
        assertThrows(DataWriteException.class, () -> employeeController.update(employee));
    }
     */

    @AfterAll
    static void tearDownAll() throws SQLException {
        MsSqlPersistenceConnection.getInstance().rollbackTransaction();
    }
}