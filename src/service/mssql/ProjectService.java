package service.mssql;

import controller.CustomerController;
import controller.EmployeeController;
import exception.DataAccessException;
import model.Customer;
import model.Employee;
import model.Order;
import model.Project;
import persistance.ProjectDao;
import persistance.ProjectDto;
import persistance.mssql.ProjectDaoMsSql;

import java.util.HashSet;
import java.util.Set;

public class ProjectService {
    private final ProjectDao projectDao = new ProjectDaoMsSql();
    private final CustomerController customerController = new CustomerController();
    private final EmployeeController employeeController = new EmployeeController();
    private final OrderServiceMsSql orderServiceMsSql = new OrderServiceMsSql();

    public Project findById(int projectId) throws DataAccessException {
        // Setup
        final Project project = new Project();
        // Get DTO for project
        final ProjectDto projectDto = projectDao.findById(projectId);
        project.setId(projectDto.getId());
        project.setName(projectDto.getName());
        project.setPrice(projectDto.getPrice());

        // Create employees for project
        Set<Employee> employees = new HashSet<>();
        for (int employeeId : projectDto.getEmployeeIds()) {
            Employee employee = employeeController.findById(employeeId);
            employees.add(employee);
        }
        project.setEmployeeSet(employees);

        // Create customers for project
        Set<Customer> customers = new HashSet<>();
        for (int customerId : projectDto.getCustomerIds()) {
            System.out.println(customerId);
            Customer customer = customerController.findById(customerId);
            customers.add(customer);
        }
        project.setCustomerSet(customers);

        // Create orders for project
        Set<Order> orders = new HashSet<>();
        for (int orderId : projectDto.getOrderIds()) {
            Order order = orderServiceMsSql.findById(orderId);
            orders.add(order);
        }
        project.setOrderSet(orders);

        return project;
    }
}
