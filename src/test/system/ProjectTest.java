package test.system;

import controller.*;
import dao.EmployeeDao;
import dao.ProjectDao;
import datasource.DBConnection;
import datasource.DBManager;
import model.*;
import model.specifications.Window;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {
	private static DBConnection connection;
	private static EmployeeDao employeeDao;
	private static ProjectDao projectDao;
	private CustomerController customerController;
	
	@BeforeAll
	static void setup() {
		// Make sure that the DB is ready before we can start
		connection = DBManager.getInstance().getPool().getConnection();
		employeeDao = connection.getDaoFactory().createEmployeeDao();
		projectDao = connection.getDaoFactory().createProjectDao();
	}
	
	@BeforeEach
	void setupEach() {
		customerController = new CustomerController();
	}
	
	private void findCustomer(String search, Consumer<Customer> callback) {
		customerController.addFindListener(customers -> {
			if (customers.isEmpty()) {
				try {
					throw new Exception("No customers!");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			callback.accept(customers.iterator().next());
		});
		customerController.getSearch(search);
	}
	
	private void getOrderControllerFromSpecifications(Consumer<OrderController> callback) {
		AtomicReference<List<Requirement>> requirements = new AtomicReference<>(new LinkedList<>());
		SpecificationsController specificationsController = new SpecificationsController();
		
		// Get all specifications
		specificationsController.addFindListener(specifications -> {
			SpecificationController specificationController = new SpecificationController(new Window());
			specificationController.setDisplayName("Test Navn");
			specificationController.setResultAmount(3);
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
			try {
				specificationController.save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		specificationsController.addSaveListener(callback::accept);
		specificationsController.getSpecifications();
	}
	
	@Test
	void testCreateProject() throws Exception {
		// Setup basics
		int estimatedHours = 25;
		String name = "Test Project";
		Price price = new Price(500 * 500);
		Employee employee = employeeDao.findById(2);
		ProjectStatus status = ProjectStatus.ON_HOLD;
		String customerPhoneNumber = "11223344";
		AtomicReference<Project> returnProject = new AtomicReference<>();
		
		// Step 1 out of 3. Find & set the customer
		CountDownLatch customerLock = new CountDownLatch(1);
		ProjectController projectController = new ProjectController();
		findCustomer(customerPhoneNumber, customer -> {
			projectController.setCustomer(customer);
			
			customerLock.countDown();
		});
		// Give it 200ms to find & set before we continue
		customerLock.await(200, TimeUnit.MILLISECONDS);
		
		// Step 2 out of 3. Specifications
		CountDownLatch specificationsLock = new CountDownLatch(1);
		getOrderControllerFromSpecifications(orderController -> {
			projectController.setOrderController(orderController);
			
			specificationsLock.countDown();
		});
		// Give it 500ms to find & set before we continue
		specificationsLock.await(400, TimeUnit.MILLISECONDS);
		
		// Step 3 out of 3. Set last details
		CountDownLatch projectLock = new CountDownLatch(1);
		projectController.setName(name);
		projectController.setPrice(price);
		projectController.setEstimatedHours(estimatedHours);
		projectController.setLeadEmployee(employee);
		projectController.setStatus(status);
		// At last save
		projectController.addSaveListener(project -> {
			returnProject.set(project);
			
			projectLock.countDown();
		});
		projectController.save();
		// Give it 200ms to find & set before we continue
		projectLock.await(600, TimeUnit.MILLISECONDS);
		
		// Asserts
		Project project = returnProject.get();
		assertNotNull(project);
		assertNotNull(project.getOrder());
		assertNotNull(project.getCustomer());
		assertNotNull(project.getEmployee());
		assertEquals(project.getEstimatedHours(), estimatedHours);
		assertSame(project.getStatus(), ProjectStatus.ON_HOLD);
	}
	
	@Test
	void testUpdateProject() throws Exception {
		// Arrange
		int projectId = 3;
		ProjectController projectController;
		Project project;
		
		// Act
		CountDownLatch lock = new CountDownLatch(1);
		projectController = new ProjectController(projectDao.findById(projectId, true));
		projectController.setStatus(ProjectStatus.FINISHED);
		projectController.addSaveListener(p -> lock.countDown());
		projectController.save();
		lock.await(500, TimeUnit.MILLISECONDS);
		project = projectDao.findById(projectId, false);
		
		// Assert
		assertNotNull(project);
		assertEquals(project.getStatus(), ProjectStatus.FINISHED);
	}
	
	@AfterAll
	static void teardown() {
		connection.release();
	}
}
