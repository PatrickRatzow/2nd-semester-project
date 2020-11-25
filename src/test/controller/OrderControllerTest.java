package test.controller;

import controller.OrderController;
import controller.ProductController;
import entity.Order;
import entity.Price;
import entity.Product;
import entity.Project;
import exception.DataAccessException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderControllerTest {
    @Mock
    private ProductController productController;
    @Mock
    private Project project;
    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setup() {
        productController = mock(ProductController.class);
        project = mock(Project.class);
        orderController = new OrderController(project, productController);
    }

    @Test
    void canAddOrderLine() throws DataAccessException {
        int id = 1;
        Product product = new Product(id, "Test Product", "Desc", new Price(100 * 100));
        Order order;
        when(productController.findById(id)).thenReturn(product);

        // Act
        orderController.addProductById(1, 5);
        order = orderController.getOrder();

        // Assert
        assertEquals(order.getOrderLines().size(), 1);
    }

    @AfterEach
    void tearDown() {
        reset(productController);
        reset(project);
    }
}
