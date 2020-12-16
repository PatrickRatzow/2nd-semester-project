package test.unit.controller;

import controller.CustomerController;
import controller.EmployeeController;
import dao.CustomerDao;
import dao.DaoFactory;
import dao.EmployeeDao;
import datasource.DBConnection;
import datasource.DBConnectionPool;
import datasource.DBManager;
import datasource.DataAccessException;
import model.Address;
import model.Customer;
import model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("UnitTest")
public class EmployeeControllerTest {
    @InjectMocks
    private EmployeeController employeeController;
    private EmployeeDao dao;
    private DBConnection connection;

    @BeforeEach
    void setup() {
        dao = mock(EmployeeDao.class);
        connection = mock(DBConnection.class);
        DaoFactory factory = mock(DaoFactory.class);
        when(connection.getDaoFactory()).thenReturn(factory);
        when(factory.createEmployeeDao()).thenReturn(dao);
        employeeController = new EmployeeController();
    }

    @Test
    void testPassesValidationCheckIfValidData() throws InterruptedException, DataAccessException {
        try (MockedStatic<DBManager> mocked = mockStatic(DBManager.class)) {
            // Arrange
            CountDownLatch lock = new CountDownLatch(1);
            int id = 1;
            String firstName = "Allan";
            String lastName = "Jensen";
            String role = "Direktør";
            Employee employee = new Employee(id , firstName, lastName, role);
            AtomicReference<Employee> returnEmployee = new AtomicReference<>();

            List<Employee> employeeList = new LinkedList<>();
            employeeList.add(employee);
            String roleTarget = "Direktør";

            DBManager manager = mock(DBManager.class);
            mocked.when(DBManager::getInstance).thenReturn(manager);
            DBConnectionPool pool = mock(DBConnectionPool.class);
            when(manager.getPool()).thenReturn(pool);
            when(pool.getConnection()).thenReturn(connection);
            when(dao.findByRole(roleTarget)).thenReturn(employeeList);
            when(manager.getConnectionThread(any(Consumer.class))).thenAnswer(ans -> new Thread(() -> {
                Consumer<DBConnection> callback = (Consumer<DBConnection>) ans.getArguments()[0];
                callback.accept(connection);

                if (connection != null) {
                    connection.release();
                }
            }));

            // Act
            employeeController.addFindListener(employees -> {
                returnEmployee.set(employees.iterator().next());

                lock.countDown();
            });
            employeeController.getDirectors();

            lock.await(100, TimeUnit.MILLISECONDS);

            // Assert
            assertNotNull(returnEmployee.get());
            assertEquals(returnEmployee.get().getRole(), roleTarget);
        }
    }


    @Test
    void testFailsIfErrorHappens() throws InterruptedException, DataAccessException {
        try (MockedStatic<DBManager> mocked = mockStatic(DBManager.class)) {
            // Arrange
            CountDownLatch lock = new CountDownLatch(1);
            AtomicReference<Exception> returnException = new AtomicReference<>();
            String roleTarget = "Direktør";

            DBManager manager = mock(DBManager.class);
            mocked.when(DBManager::getInstance).thenReturn(manager);
            DBConnectionPool pool = mock(DBConnectionPool.class);
            when(manager.getPool()).thenReturn(pool);
            when(pool.getConnection()).thenReturn(connection);
            when(dao.findByRole(roleTarget)).thenThrow(DataAccessException.class);
            when(manager.getConnectionThread(any(Consumer.class))).thenAnswer(ans -> new Thread(() -> {
                Consumer<DBConnection> callback = (Consumer<DBConnection>) ans.getArguments()[0];
                callback.accept(connection);

                if (connection != null) {
                    connection.release();
                }
            }));

            // Act
            employeeController.addErrorListener(exception -> {
                returnException.set(exception);

                lock.countDown();
            });
            employeeController.getDirectors();

            lock.await(100, TimeUnit.MILLISECONDS);

            // Assert
            assertNotNull(returnException.get());
        }
    }

    @AfterEach
    void teardown() {
        reset(dao);
        reset(connection);
    }
}
