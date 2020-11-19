package persistence.repository.mssql;

import exception.DataAccessException;
import model.*;
import persistence.dao.CustomerDao;
import persistence.dao.EmployeeDao;
import persistence.dao.ProjectDao;
import persistence.dao.mssql.MsSqlCustomerDao;
import persistence.dao.mssql.MsSqlEmployeeDao;
import persistence.dao.mssql.MsSqlProjectDao;
import persistence.repository.OrderRepository;
import persistence.repository.ProjectRepository;
import persistence.repository.mssql.dto.ProjectDto;

import java.util.HashSet;
import java.util.Set;

public class MsSqlProjectRepository implements ProjectRepository {
    private final ProjectDao projectDao = new MsSqlProjectDao();
    private final CustomerDao customerDao = new MsSqlCustomerDao();
    private final EmployeeDao employeeDao = new MsSqlEmployeeDao();
    private final OrderRepository orderRepository = new MsSqlOrderRepository();

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
            final Order order = orderRepository.findById(orderId);
            orders.add(order);
        }
        project.setOrderSet(orders);

        return project;
    }
}
