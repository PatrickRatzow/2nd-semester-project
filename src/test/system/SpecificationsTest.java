package test.system;

import controller.SpecificationController;
import controller.SpecificationsController;
import datasource.DBConnection;
import datasource.DBManager;
import model.OrderLine;
import model.Requirement;
import model.Specification;
import model.specifications.Window;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class SpecificationsTest {
	@BeforeAll
	static void setup() {
		// Make sure that the DB is ready before we can start
		DBConnection connection = DBManager.getInstance().getPool().getConnection();
		connection.release();
	}
	
	@Test
	void testFindCheapestProductsHappyDays() throws InterruptedException {
		// Setup variables
		CountDownLatch lock = new CountDownLatch(1);
		Specification specification = new Window();
		String displayName = "Test Specification";
		int resultAmount = 3;
		AtomicReference<List<Requirement>> requirements = new AtomicReference<>(new LinkedList<>());
		AtomicReference<OrderLine> orderLine = new AtomicReference<>();
		Stack<String> errors = new Stack<>();
		SpecificationsController specificationsController = new SpecificationsController();
		List<String> requirementNames = Arrays.asList("Color", "Width", "Height");
		
		// Get all specifications
		specificationsController.addFindListener(specifications -> {
			SpecificationController specificationController = new SpecificationController(specification);
			specificationController.setDisplayName(displayName);
			specificationController.setResultAmount(resultAmount);
			List<Requirement> requirementsTemp = specificationController.getRequirements();
			for (Requirement requirement : requirementsTemp) {
				String name = requirement.getName();
				switch (name) {
					case "Color":
						requirement.setValueFromSQLValue("Rød");
						break;
						
					case "Width":
						requirement.setValueFromSQLValue("150");
						break;
						
					case "Height":
						requirement.setValueFromSQLValue("100");
						break;
				}
			}
			requirements.set(requirementsTemp);
			specificationController.addSaveListener(specController -> {
				specificationsController.addSpecificationController(specController);
				specificationsController.getProductsFromSpecifications();
			});
			specificationsController.addSpecificationController(specificationController);
			specificationsController.addErrorListener(e -> errors.add(e.getMessage()));
			try {
				specificationController.save();
			} catch (Exception e) {
				errors.add(e.getMessage());
			}
		});
		specificationsController.addSaveListener(orderController -> {
			orderLine.set(orderController.getOrderLines().iterator().next());
			
			lock.countDown();
		});
		specificationsController.getSpecifications();
		
		lock.await(1000, TimeUnit.MILLISECONDS);
		
		// If this isn't empty some kind of error happened
		if (!errors.isEmpty()) {
			fail(String.join("\n", errors));
		}
		
		// Check that the requirements on the specifications are the needed ones
		assertTrue(requirements.get().stream()
				.map(Requirement::getName)
				.collect(Collectors.toList())
				.containsAll(requirementNames));
		// Make sure that there are 3 requirements
		assertEquals(requirements.get().size(), 3);
		// Make sure that orderLine isn't null
		assertNotNull(orderLine.get());
		// Make sure that the orderLine has the right amount
		assertEquals(orderLine.get().getQuantity(), resultAmount);
		// Make sure that we have the right fields on the product
		assertTrue(orderLine.get().getProduct().getFields().size() >= 3);
		// Make sure these are the fields we think they are
		orderLine.get().getProduct().getFields().forEach((key, obj) -> {
			assertTrue(requirementNames.stream().anyMatch(name -> name.equalsIgnoreCase(key)));
		});
	}
}
