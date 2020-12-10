package test.system;

import controller.CustomerController;
import controller.ProjectController;
import datasource.DBConnection;
import datasource.DBManager;
import model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ProjectTest {
	private CustomerController customerController;
	
	@BeforeEach
	void setup() {
		// Make sure that the DB is ready before we can start
		DBConnection connection = DBManager.getInstance().getPool().getConnection();
		connection.release();
		
		// Setup vars
		customerController = new CustomerController();
	}
	
	private void findCustomer(String search, Consumer<Customer> callback) {
	
	}
	
	@Test
	void testProjectTest() throws InterruptedException {
		// Setup basics
		CountDownLatch lock = new CountDownLatch(1);
		
		// Create controller
		ProjectController projectController = new ProjectController();
		
		// Step 1 out of 3. Set the customer
		String customerPhoneNumber = "11223344";
		customerController.addFindListener(customers -> {
		
		});
		customerController.getSearch(customerPhoneNumber);
		
		// Step 2 out of 3. Specifications
		
		// Step 3 out of 3. Set last details
		
		lock.await(500, TimeUnit.MILLISECONDS);
		
		System.out.println("XD");
	}
}
