package service.mssql;

import controller.CustomerController;
import controller.EmployeeController;
import exception.DataAccessException;
import exception.DataWriteException;
import model.*;
import persistance.OrderDao;
import persistance.OrderDto;
import persistance.mssql.OrderDaoMsSql;
import service.OrderService;

import java.util.HashSet;
import java.util.Set;

public class OrderServiceMsSql implements OrderService {
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
        Set<OrderLine> orderLines = new HashSet<>(orderLineService.findByOrderId(orderDto.getId()));
        order.setOrderLines(orderLines);

        // Get the invoices
        OrderInvoice orderInvoice = orderInvoiceService.findById(orderDto.getId());
        order.setOrderInvoice(orderInvoice);

        return order;
    }

    public Order findById(int orderId) throws DataAccessException {
        return buildObject(orderDao.findById(orderId));
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

        // Create invoice
        orderInvoiceService.create(order, order.getOrderInvoice());

        return order;
    }

    public void update(Order order) {

    }
}
