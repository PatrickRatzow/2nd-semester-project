package test.unit.controller;

import controller.ProductController;
import controller.SpecificationController;
import controller.SpecificationsController;
import model.OrderLine;
import model.Product;
import model.Specification;
import model.specifications.Window;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SpecificationsControllerTest {
	private ProductController productController;
	private SpecificationController specificationController;
	
	@BeforeEach
	void setup() {
		productController = mock(ProductController.class);
		specificationController = mock(SpecificationController.class);
		when(specificationController.getSpecification()).thenReturn(new Window());
		when(specificationController.getDisplayName()).thenReturn("Test");
		when(specificationController.getResultAmount()).thenReturn(3);
	}
	
	@Test
	void testGivesProductsIfProductCanBeFound() throws InterruptedException {
		// Arrange
		CountDownLatch lock = new CountDownLatch(1);
		SpecificationsController specificationsController = new SpecificationsController(productController);
		specificationsController.addSpecificationController(specificationController);
		Product product = new Product();
		doAnswer(ans -> {
			Consumer<Product> callback = (Consumer<Product>) ans.getArguments()[1];
			callback.accept(product);
			
			return null;
		}).when(productController).getBySpecification(any(Specification.class), any(Consumer.class));
		AtomicReference<OrderLine> orderLine = new AtomicReference<>();
		// Act
		specificationsController.addSaveListener(orderController -> {
			orderLine.set(orderController.getOrderLines().iterator().next());
			
			lock.countDown();
		});
		specificationsController.getProductsFromSpecifications();
		
		lock.await(100, TimeUnit.MILLISECONDS);
		
		// Assert
		assertNotNull(orderLine.get());
		assertEquals(product, orderLine.get().getProduct());
	}
	
	@Test
	void testErrorsIfProductCanBeFound() throws InterruptedException {
		// Arrange
		CountDownLatch lock = new CountDownLatch(1);
		SpecificationsController specificationsController = new SpecificationsController(productController);
		specificationsController.addSpecificationController(specificationController);
		doAnswer(ans -> {
			Consumer<Product> callback = (Consumer<Product>) ans.getArguments()[1];
			callback.accept(null);
			
			return null;
		}).when(productController).getBySpecification(any(Specification.class), any(Consumer.class));
		AtomicReference<Exception> exception = new AtomicReference<>();
		// Act
		specificationsController.addErrorListener(e -> {
			exception.set(e);
			
			lock.countDown();
		});
		specificationsController.getProductsFromSpecifications();
		
		lock.await(1000, TimeUnit.MILLISECONDS);
		
		// Assert
		assertNotNull(exception.get());
	}
}
