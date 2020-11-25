package test.controller;

import controller.OrderController;
import controller.ProductController;
import entity.Order;
import entity.Price;
import entity.Product;
import entity.Project;
import exception.DataAccessException;
import exception.DataWriteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import service.OrderService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
    @Mock
    private ProductController productController;
    @Mock
    private OrderService orderService;

    @BeforeEach
    void setup() {
        productController = mock(ProductController.class);
        orderService = mock(OrderService.class);
    }

    @Test
    void canAddOrderLine() throws DataAccessException {
        // Arrange
        OrderController orderController = new OrderController();
        int id = 1;
        Product product = new Product(id, "Test Product", "Desc", new Price(100 * 100));
        when(productController.findById(id)).thenReturn(product);
        Order order;

        // Act
        orderController.addProductById(1, 5);
        order = orderController.getOrder();

        // Assert
        assertEquals(order.getOrderLines().size(), 1);
    }
}
