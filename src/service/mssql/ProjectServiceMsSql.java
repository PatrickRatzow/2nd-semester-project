package service.mssql;

import dao.CustomerDao;
import dao.EmployeeDao;
import dao.ProjectDao;
import dao.mssql.CustomerDaoMsSql;
import dao.mssql.EmployeeDaoMsSql;
import dao.mssql.ProjectDaoMsSql;
import dto.ProjectDto;
import exception.DataAccessException;
import entity.*;
import service.OrderService;
import service.ProjectService;

import java.util.HashSet;
import java.util.Set;

public class ProjectServiceMsSql implements ProjectService {
    private final ProjectDao projectDao = new ProjectDaoMsSql();
    private final CustomerDao customerDao = new CustomerDaoMsSql();
    private final EmployeeDao employeeDao = new EmployeeDaoMsSql();
    private final OrderService orderService = new OrderServiceMsSql();

    public Project findById(int projectId) throws DataAccessException {
        // Setup
        final Project project = new Project();

        // Get DTO for project
        final ProjectDto projectDto = projectDao.findById(projectId);
        project.setId(projectDto.getId());
        project.setName(projectDto.getName());
        project.setPrice(new Price(projectDto.getPrice()));

        // Create employees for project
        final Set<Employee> employees = new HashSet<>();
        for (final int employeeId : projectDto.getEmployeeIds()) {
            final Employee employee = employeeDao.findById(employeeId);
            employees.add(employee);
        }
        project.setEmployeeSet(employees);

        // Create customers for project
        final Set<Customer> customers = new HashSet<>();
        for (final int customerId : projectDto.getCustomerIds()) {
            final Customer customer = customerDao.findById(customerId);
            customers.add(customer);
        }
        project.setCustomerSet(customers);

        // Create orders for project
        final Set<Order> orders = new HashSet<>();
        for (final int orderId : projectDto.getOrderIds()) {
            final Order order = orderService.findById(orderId);
            orders.add(order);
        }
        project.setOrderSet(orders);

        return project;
    }
}
