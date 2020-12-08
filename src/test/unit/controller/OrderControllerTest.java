package test.unit.controller;

import controller.OrderController;
import model.Order;
import model.Price;
import model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("UnitTest")
public class OrderControllerTest {
    private OrderController orderController;

    @BeforeEach
    void setup() {
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
}
