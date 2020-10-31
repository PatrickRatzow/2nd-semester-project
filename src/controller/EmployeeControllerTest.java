package controller;

import database.DBConnection;
import database.DataAccessException;
import database.DataWriteException;
import model.Employee;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeControllerTest {
    private EmployeeController employeeController;

    @BeforeAll
    static void setUpAll() throws SQLException {
        DBConnection.getInstance().startTransaction();
    }

    @BeforeEach
    void setUp() {
        employeeController = new EmployeeController();
    }

    @Test
    // Enforce order as methods below us inserts into database, and we need to do our expected finds first
    @Order(1)
    @DisplayName("findAll() works")
    void canFindAllEmployeesInDatabase() throws DataAccessException {
        List<Employee> employees;
        int expectedSize = 1;

        employees = employeeController.findAll();

        assertEquals(employees.size(), expectedSize);
    }

    @Test
    @Order(2)
    @DisplayName("create() works")
    void canCreateEmployee() throws DataWriteException {
        String firstName = "Allan";
        String lastName = "Jensen";
        // Surprisingly a valid email
        String email = "allanjensen@ucn@example.com";
        String phoneNo = "55555555";
        String username = "allanjensen";
        String password = "hunter2";
        Employee employee = new Employee(firstName, lastName, email, phoneNo, username);
        Employee returnEmployee;

        // Act
        returnEmployee = employeeController.create(employee, password);

        // Assert
        assertNotNull(returnEmployee);
    }

    @Test
    @Order(2)
    @DisplayName("create() throws IllegalArgumentException if there's already an employee with that username")
    void cantCreateEmployee() {
        String firstName = "Cas";
        String lastName = "Cas";
        String email = "cas@example.com";
        String phoneNo = "45454545";
        String username = "cas789";
        String password = "hunter2";
        Employee employee = new Employee(firstName, lastName, email, phoneNo, username);

        // Act
        assertThrows(IllegalArgumentException.class, () -> employeeController.create(employee, password));
    }

    @Test
    @DisplayName("create() throws IllegalArgumentException if using an invalid email")
    void cantCreateIfInvalidEmail() {
        String firstName = "Cas";
        String lastName = "Cas";
        String email = "#@%^%#$@#$@#.com";
        String phoneNo = "45454545";
        String username = "cas7891";
        String password = "hunter2";
        Employee employee = new Employee(firstName, lastName, email, phoneNo, username);

        // Act
        assertThrows(IllegalArgumentException.class, () -> employeeController.create(employee, password));
    }

    @Test
    @DisplayName("findByUsernameAndPassword() works if user exists & password is correct")
    void canFindByUsernameAndPassword() throws DataAccessException, WrongPasswordException {
        String username = "Cas789";
        String password = "hunter2";
        Employee employee;

        employee = employeeController.findByUsernameAndPassword(username, password);

        assertTrue(employee instanceof Employee);
    }

    @Test
    @DisplayName("findByUsernameAndPassword() throws a WrongPasswordException if user exists, but password is incorrect")
    void cantFindByUsernameAndPasswordIfPasswordWrong() {
        String username = "Cas789";
        String password = "hunter3";

        assertThrows(WrongPasswordException.class, () -> employeeController.findByUsernameAndPassword(username, password));
    }

    @Test
    @DisplayName("findByUsernameAndPassword() throws a DataAccessException if user doesn't exist")
    void cantFindByUsernameAndPasswordIfUserDoesNotExist() {
        String username = "Cas78910";
        String password = "hunter2";

        assertThrows(DataAccessException.class, () -> employeeController.findByUsernameAndPassword(username, password));
    }

    @Test
    @DisplayName("update() works if id exists in db")
    void canUpdateExisting() throws DataWriteException, DataAccessException {
        int id = 1;
        String firstName = "Cas";
        String lastName = "Cas";
        String email = "cas@example.com";
        String phoneNo = "45454545";
        String username = "cas789";
        String password = "hunter2";
        Employee employee = new Employee(firstName, lastName, email, phoneNo, username);
        employee.setId(id);

        // Act
        employeeController.update(employee, password);

        // It would throw an exception if it failed
    }

    @Test
    @DisplayName("update() throws DataWriteException if id doesnt exist")
    void cantUpdateNonExisting() {
        int id = 50203423;
        String firstName = "Cas";
        String lastName = "Cas";
        String email = "cas@example.com";
        String phoneNo = "45454545";
        String username = "cas789";
        String password = "hunter2";
        Employee employee = new Employee(firstName, lastName, email, phoneNo, username);
        employee.setId(id);

        // Act + assert
        assertThrows(DataWriteException.class, () -> employeeController.update(employee, password));
    }

    @AfterAll
    static void tearDownAll() throws SQLException {
        DBConnection.getInstance().commitTransaction();
    }
}