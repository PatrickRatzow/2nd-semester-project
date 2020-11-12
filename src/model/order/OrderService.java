package model.order;

import controller.CustomerController;
import controller.EmployeeController;
import exception.DataAccessException;
import exception.DataWriteException;
import model.customer.Customer;
import model.employee.Employee;
import model.order_invoice.OrderInvoice;
import model.order_invoice.OrderInvoiceService;
import model.order_line.OrderLine;
import model.order_line.OrderLineService;
import model.project.Project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderService {
    private final OrderDao orderDao = new OrderDaoMsSql();
    private final OrderLineService orderLineService = new OrderLineService();
    private final OrderInvoiceService orderInvoiceService = new OrderInvoiceService();
    private final CustomerController customerController = new CustomerController();
    private final EmployeeController employeeController = new EmployeeController();

    private Order buildObject(OrderDto orderDto) throws DataAccessException {
        // Setup
        final Order order = new Order();

        // Get our order
        Employee employee = employeeController.findById(orderDto.getEmployeeId());
        Customer customer = customerController.findById(orderDto.getCustomerId());
        // Start populating the order
        order.setId(orderDto.getId());
        order.setDate(orderDto.getCreatedAt());
        order.setStatus(orderDto.getStatus());
        order.setCustomer(customer);
        order.setEmployee(employee);

        // Now find all our order lines for that order
        Set<OrderLine> orderLines = new HashSet<>(orderLineService.findAllByOrderId(orderDto.getId()));
        order.setOrderLines(orderLines);

        // Get the invoices
        Set<OrderInvoice> orderInvoices = new HashSet<>(orderInvoiceService.findAllByOrderId(orderDto.getId()));
        order.setOrderInvoices(orderInvoices);

        return order;
    }

    public Order findById(int orderId) throws DataAccessException {
        return buildObject(orderDao.findById(orderId));
    }

    public List<Order> findByProjectId(int orderId) throws DataAccessException {
        List<Order> orders = new ArrayList<>();
        // Setup
        List<OrderDto> orderDtos = orderDao.findAllByProjectId(orderId);
        for (OrderDto orderDto : orderDtos) {
            orders.add(buildObject(orderDto));
        }

        return orders;
    }

    public Order create(Order order, Project project) throws DataWriteException {
        // Create new order
        Order newOrder = orderDao.create(order.getDate(), order.getStatus(), order.getCustomer().getId(),
                order.getEmployee().getId(), project.getId());
        // We only need the id from newOrder, so just merge it into the order parameter so we don't have to assemble it.
        order.setId(newOrder.getId());

        // Create all the order lines
        for (OrderLine orderLine : order.getOrderLines()) {
            orderLineService.create(order, orderLine);
        }

        // Create all the invoices
        for (OrderInvoice orderInvoice : order.getOrderInvoices()) {
            orderInvoiceService.create(order, orderInvoice);
        }

        return order;
    }

    public void update(Order order) {

    }
}
