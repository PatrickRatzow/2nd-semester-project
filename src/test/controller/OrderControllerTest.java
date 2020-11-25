package test.controller;

import controller.OrderController;
import controller.ProductController;
import entity.Order;
import entity.Price;
import entity.Product;
import exception.DataAccessException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
    @Mock
    private ProductController productController;
    @InjectMocks
    private OrderController orderController;

    @Test
    void canAddOrderLine() throws DataAccessException {
        // Arrange
        int id = 1;
        Product product = new Product(id, "Test Product", "Desc", new Price(100 * 100));
        Order order;
        when(productController.findById(id)).thenReturn(product);

        // Act
        orderController.addProductById(id, 5);
        order = orderController.getOrder();

        // Assert
        assertEquals(order.getOrderLines().size(), 1);
    }

    @Test
    void cantAddOrderLineIfProductDoesntExist() throws DataAccessException {
        // Arrange
        int id = 1000;
        when(productController.findById(id)).thenThrow(DataAccessException.class);

        // Assert + Act
        assertThrows(DataAccessException.class, () -> orderController.addProductById(id, 1));
    }

    @AfterEach
    void tearDown() {
        reset(productController);
    }
}
