package test.unit.controller;

import controller.OrderController;
import controller.ProductController;
import entity.Order;
import entity.Price;
import entity.Product;
import exception.DataWriteException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.reset;

@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
    @Mock
    private ProductController productController;
    @InjectMocks
    private OrderController orderController;

    @Test
    void testCanAddOrderLine() {
        // Arrange
        Product product = new Product(1, "Test Product", "Desc", new Price(100 * 100));
        Order order;

        // Act
        orderController.addProduct(product, 5);
        order = orderController.getOrder();

        // Assert
        assertEquals(order.getOrderLines().size(), 1);
    }

    @Test
    void testCantAddOrderLineIfProductIsNull() {
        // Assert + Act
        assertThrows(IllegalArgumentException.class, () ->
                orderController.addProduct(null, 1)
        );
    }

    @Test
    void testCantAddOrderLineIfQuantityIs0OrBelow() {
        // Arrange
        Product product = new Product(42, "Product", "Desc", new Price(120 * 100));

        // Assert + Act
        assertThrows(IllegalArgumentException.class, () ->
                orderController.addProduct(product, 0)
        );
    }

    @Test
    void testCanAddOrderLineWithSameProductMultipleTimesAndStillHaveOnlyOneOrderLineForThatProduct() {
        // Arrange
        Product product = new Product(2, "The Product", "Desc", new Price(500 * 100));
        Order order;

        // Act
        orderController.addProduct(product, 5);
        orderController.addProduct(product, 2);
        orderController.addProduct(product, 55);
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
        orderController.addProduct(product, 5);
        orderController.addProduct(product, 6);
        order = orderController.getOrder();

        // Assert
        assertEquals(order.getOrderLines().get(id).getQuantity(), 11);
    }

    @Test
    void createOrder() throws DataWriteException {
        //orderController.create();
    }

    @AfterEach
    void tearDown() {
        reset(productController);
    }
}
