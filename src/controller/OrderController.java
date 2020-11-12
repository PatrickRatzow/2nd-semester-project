package controller;

import database.*;
import model.*;

import java.util.HashSet;
import java.util.Set;


public class OrderController {
    private final OrderDao orderDao = new OrderDaoMsSql();
    private final OrderLineController orderLineController = new OrderLineController();
    private final OrderInvoiceController orderInvoiceController = new OrderInvoiceController();
    private final CustomerController customerController = new CustomerController();
    private final EmployeeController employeeController = new EmployeeController();

    public Order findById(int orderId) throws DataAccessException {
        // Setup
        final Order order = new Order();

        // Get our order
        OrderDto orderDto = orderDao.findById(orderId);
        Employee employee = employeeController.findById(orderDto.getEmployeeId());
        Customer customer = customerController.findById(orderDto.getCustomerId());
        // Start populating the order
        order.setId(orderDto.getId());
        order.setDate(orderDto.getCreatedAt());
        order.setStatus(orderDto.getStatus());
        order.setCustomer(customer);
        order.setEmployee(employee);

        // Now find all our order lines for that order
        Set<OrderLine> orderLines = new HashSet<>(orderLineController.findAllByOrderId(orderId));
        order.setOrderLines(orderLines);

        // Get the invoices
        Set<OrderInvoice> orderInvoices = new HashSet<>(orderInvoiceController.findAllByOrderId(orderId));
        order.setOrderInvoices(orderInvoices);

        return order;
    }

    public Order create(Order order, Project project) throws DataWriteException {
        // Create new order
        Order newOrder = orderDao.create(order.getDate(), order.getStatus(), order.getCustomer().getId(),
                order.getEmployee().getId(), project.getId());
        // We only need the id from newOrder, so just merge it into the order parameter so we don't have to assemble it.
        order.setId(newOrder.getId());

        // Create all the order lines
        for (OrderLine orderLine : order.getOrderLines()) {
            orderLineController.create(order, orderLine);
        }

        // Create all the invoices
        for (OrderInvoice orderInvoice : order.getOrderInvoices()) {
            orderInvoiceController.create(order, orderInvoice);
        }

        return order;
    }

    public void update(Order order) {

    }
}
