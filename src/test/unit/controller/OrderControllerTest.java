package test.unit.controller;

import controller.OrderController;
import dao.DaoFactory;
import dao.OrderDao;
import datasource.DBConnection;
import datasource.DBConnectionPool;
import datasource.DBManager;
import datasource.DataAccessException;
import model.Order;
import model.Price;
import model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("UnitTest")
public class OrderControllerTest {
    private OrderController orderController;
    private OrderDao dao;
    private DBConnection connection;
    
    @BeforeEach
    void setup() {
        dao = mock(OrderDao.class);
        connection = mock(DBConnection.class);
        DaoFactory factory = mock(DaoFactory.class);
        when(connection.getDaoFactory()).thenReturn(factory);
        when(factory.createOrderDao()).thenReturn(dao);
        orderController = new OrderController();
    }
    
    @Test
    void testCanAddOrderLine() {
        // Arrange
        Product product = new Product(1, "Test Product", "Desc", new Price(100 * 100));
        Order order;

        // Act
        orderController.addProduct(product, 5, "Test");
        order = orderController.getOrder();

        // Assert
        assertEquals(order.getOrderLines().size(), 1);
    }

    @Test
    void testCantAddOrderLineIfProductIsNull() {
        // Assert + Act
        assertThrows(IllegalArgumentException.class, () ->
                orderController.addProduct(null, 1, "Test")
        );
    }

    @Test
    void testCantAddOrderLineIfQuantityIs0OrBelow() {
        // Arrange
        Product product = new Product(42, "Product", "Desc", new Price(120 * 100));

        // Assert + Act
        assertThrows(IllegalArgumentException.class, () ->
                orderController.addProduct(product, 0, "Test")
        );
    }

    @Test
    void testCanAddOrderLineWithSameProductMultipleTimesAndStillHaveOnlyOneOrderLineForThatProduct() {
        // Arrange
        Product product = new Product(2, "The Product", "Desc", new Price(500 * 100));
        Order order;

        // Act
        orderController.addProduct(product, 5, "Test");
        orderController.addProduct(product, 2, "Test");
        orderController.addProduct(product, 55, "Test");
        order = orderController.getOrder();

        // Assert
        assertEquals(order.getOrderLines().size(), 1);
    }

    @Test
    void testCanAddOrderLineWithSameProductMultipleTimesAndTheSharedOrderLineHasTheCorrectQuantity() {
        // Arrange
        int id = 4;
        Product product = new Product(id, "The Product 45", "Desc", new Price(500 * 100));
        Order order;

        // Act
        orderController.addProduct(product, 5, "Test");
        orderController.addProduct(product, 6, "Test");
        order = orderController.getOrder();

        // Assert
        assertEquals(order.getOrderLines().get(id).getQuantity(), 11);
    }
    
    @Test
    void testSucceedsFindingOrderByIdIfItExists() throws DataAccessException {
        try (MockedStatic<DBManager> mocked = mockStatic(DBManager.class)) {
            // Arrange
            int id = 1;
            boolean fullAssociation = true;
            Order order;
            
            DBManager manager = mock(DBManager.class);
            mocked.when(DBManager::getInstance).thenReturn(manager);
            DBConnectionPool pool = mock(DBConnectionPool.class);
            when(manager.getPool()).thenReturn(pool);
            when(pool.getConnection()).thenReturn(connection);
            when(dao.findById(id, fullAssociation)).thenReturn(new Order());
            when(manager.getConnectionThread(any(Consumer.class))).thenAnswer(ans -> new Thread(() -> {
                Consumer<DBConnection> callback = (Consumer<DBConnection>) ans.getArguments()[0];
                callback.accept(connection);
            
                if (connection != null) {
                    connection.release();
                }
            }));
        
            // Act
            order = orderController.findById(id, fullAssociation);
            
            // Assert
            assertNotNull(order);
        }
    }
    
    @Test
    void testFailsFindingOrderByIdIfDoesntExist() throws DataAccessException {
        try (MockedStatic<DBManager> mocked = mockStatic(DBManager.class)) {
            // Arrange
            int id = 1;
            boolean fullAssociation = true;
            Order order;
            
            DBManager manager = mock(DBManager.class);
            mocked.when(DBManager::getInstance).thenReturn(manager);
            DBConnectionPool pool = mock(DBConnectionPool.class);
            when(manager.getPool()).thenReturn(pool);
            when(pool.getConnection()).thenReturn(connection);
            when(dao.findById(id, fullAssociation)).thenReturn(null);
            when(manager.getConnectionThread(any(Consumer.class))).thenAnswer(ans -> new Thread(() -> {
                Consumer<DBConnection> callback = (Consumer<DBConnection>) ans.getArguments()[0];
                callback.accept(connection);
                
                if (connection != null) {
                    connection.release();
                }
            }));
            
            // Act
            order = orderController.findById(id, fullAssociation);
            
            // Assert
            assertNull(order);
        }
    }
}
