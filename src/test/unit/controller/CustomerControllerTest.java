package test.unit.controller;

import controller.CustomerController;
import dao.CustomerDao;
import dao.DaoFactory;
import datasource.DBConnection;
import datasource.DBConnectionPool;
import datasource.DBManager;
import datasource.DataAccessException;
import model.Address;
import model.Customer;
import org.junit.jupiter.api.*;
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
public class CustomerControllerTest {
    @InjectMocks
    private CustomerController customerController;
    private CustomerDao dao;
    private DBConnection connection;
    
    @BeforeEach
    void setup() {
        dao = mock(CustomerDao.class);
        connection = mock(DBConnection.class);
        DaoFactory factory = mock(DaoFactory.class);
        when(connection.getDaoFactory()).thenReturn(factory);
        when(factory.createCustomerDao()).thenReturn(dao);
        customerController = new CustomerController();
    }
    
    @Test
    void testPassesValidationCheckIfValidData() throws InterruptedException, DataAccessException {
        try (MockedStatic<DBManager> mocked = mockStatic(DBManager.class)) {
            // Arrange
            CountDownLatch lock = new CountDownLatch(1);
            String firstName = "Allan";
            String lastName = "Jensen";
            String email = "email@email.xd";
            String phoneNumber = "45454545";
            String city = "Aalborg SV";
            String streetName = "Sofiendalsvej";
            String streetNumber = "60";
            String zipCode = "9200";
            Customer customer = new Customer(firstName, lastName, email, phoneNumber,
                    new Address(streetName, Integer.parseInt(streetNumber), city, Integer.parseInt(zipCode)));
            AtomicReference<Customer> returnCustomer = new AtomicReference<>();
            
            DBManager manager = mock(DBManager.class);
            mocked.when(DBManager::getInstance).thenReturn(manager);
            DBConnectionPool pool = mock(DBConnectionPool.class);
            when(manager.getPool()).thenReturn(pool);
            when(pool.getConnection()).thenReturn(connection);
            when(dao.create(any(Customer.class))).thenReturn(customer);
            when(manager.getConnectionThread(any(Consumer.class))).thenAnswer(ans -> new Thread(() -> {
                Consumer<DBConnection> callback = (Consumer<DBConnection>) ans.getArguments()[0];
                callback.accept(connection);

                if (connection != null) {
                    connection.release();
                }
            }));
    
            // Act
            customerController.setCustomerInformation(firstName, lastName, email, phoneNumber, city, streetName,
                    streetNumber, zipCode);
            customerController.addSaveListener(c -> {
                returnCustomer.set(c);
        
                lock.countDown();
            });
            customerController.addErrorListener(e -> {
                fail(e.getMessage());
            });
            customerController.save();
    
            lock.await(100, TimeUnit.MILLISECONDS);
    
            // Assert
            assertNotNull(returnCustomer.get());
            verify(dao, times(1)).create(any(Customer.class));
        }
    }
    
    @Test
    void testFailsValidationCheckIfEmailIsInvalid() {
        // Arrange
        String firstName = "Allan";
        String lastName = "Jensen";
        String email = "email";
        String phoneNumber = "45454545";
        String city = "Aalborg SV";
        String streetName = "Sofiendalsvej";
        String streetNumber = "60";
        String zipCode = "9200";
        boolean success;
        
        // Act
        success = customerController.setCustomerInformation(firstName, lastName, email, phoneNumber, city, streetName,
                streetNumber, zipCode);

        // Assert
        assertFalse(success);
    }

    @Test
    void testSucceedsUpdateIfValidData() throws InterruptedException, DataAccessException {
        try (MockedStatic<DBManager> mocked = mockStatic(DBManager.class)) {
            // Arrange
            CountDownLatch lock = new CountDownLatch(1);
            int id = 69;
            String firstName = "Allan";
            String lastName = "Jensen";
            String email = "email@email.xd";
            String phoneNumber = "45454545";
            String city = "Aalborg SV";
            String streetName = "Sofiendalsvej";
            String streetNumber = "60";
            String zipCode = "9200";
            Customer customer = new Customer(id, firstName, lastName, email, phoneNumber,
                    new Address(streetName, Integer.parseInt(streetNumber), city, Integer.parseInt(zipCode)));
            AtomicReference<Customer> returnCustomer = new AtomicReference<>();

            DBManager manager = mock(DBManager.class);
            mocked.when(DBManager::getInstance).thenReturn(manager);
            DBConnectionPool pool = mock(DBConnectionPool.class);
            when(manager.getPool()).thenReturn(pool);
            when(pool.getConnection()).thenReturn(connection);
            when(dao.create(any(Customer.class))).thenReturn(customer);
            when(manager.getConnectionThread(any(Consumer.class))).thenAnswer(ans -> new Thread(() -> {
                Consumer<DBConnection> callback = (Consumer<DBConnection>) ans.getArguments()[0];
                callback.accept(connection);

                if (connection != null) {
                    connection.release();
                }
            }));

            // Act
            customerController.setCustomerInformation(id, firstName, lastName, email, phoneNumber, city, streetName,
                    streetNumber, zipCode);
            customerController.addSaveListener(c -> {
                returnCustomer.set(c);

                lock.countDown();
            });
            customerController.addErrorListener(e -> {
                fail(e.getMessage());
            });
            customerController.save();

            lock.await(100, TimeUnit.MILLISECONDS);

            // Assert
            assertNotNull(returnCustomer.get());
            verify(dao, times(1)).update(any(Customer.class));
        }
    }

    @Test
    void testSucceedsIfGetAllGetsCalled() throws InterruptedException, DataAccessException {
        try (MockedStatic<DBManager> mocked = mockStatic(DBManager.class)) {
            // Arrange
            CountDownLatch lock = new CountDownLatch(1);
            int id = 1;
            String firstName = "Allan";
            String lastName = "Jensen";
            String email = "email@email.xd";
            String phoneNumber = "45454545";
            String city = "Aalborg SV";
            String streetName = "Sofiendalsvej";
            String streetNumber = "60";
            String zipCode = "9200";
            Customer customer = new Customer(id, firstName, lastName, email, phoneNumber,
                    new Address(streetName, Integer.parseInt(streetNumber), city, Integer.parseInt(zipCode)));
            AtomicReference<Customer> returnCustomer = new AtomicReference<>();

            List<Customer> customerList = new LinkedList<>();
            customerList.add(customer);
    
            DBManager manager = mock(DBManager.class);
            mocked.when(DBManager::getInstance).thenReturn(manager);
            DBConnectionPool pool = mock(DBConnectionPool.class);
            when(manager.getPool()).thenReturn(pool);
            when(pool.getConnection()).thenReturn(connection);
            when(dao.findAll()).thenReturn(customerList);
            when(manager.getConnectionThread(any(Consumer.class))).thenAnswer(ans -> new Thread(() -> {
                Consumer<DBConnection> callback = (Consumer<DBConnection>) ans.getArguments()[0];
                callback.accept(connection);

                if (connection != null) {
                    connection.release();
                }
            }));

            // Act
            customerController.addFindListener(customers -> {
                returnCustomer.set(customers.iterator().next());

                lock.countDown();
            });
            customerController.addErrorListener(Assertions::fail);
            customerController.getAll();

            lock.await(200, TimeUnit.MILLISECONDS);

            // Assert
            assertNotNull(returnCustomer.get());
            verify(dao, times(1)).findAll();
        }
    }
    
    @AfterEach
    void teardown() {
        reset(dao);
        reset(connection);
    }
}
