package services;

import database.*;
import model.*;

import java.util.HashSet;
import java.util.Set;


public class OrderService {
    private final OrderDao orderDao;
    private final OrderLineService orderLineService;
    private final OrderInvoiceService orderInvoiceService;
    private final CustomerDao customerDao;
    private final EmployeeDao employeeDao;

    public OrderService(OrderDao orderDao, OrderLineDao orderLineDao, OrderInvoiceDao orderInvoiceDao,
                        CustomerDao customerDao, EmployeeDao employeeDao,
                        ProductDao productDao) {
        this.orderDao = orderDao;
        this.orderLineService = new OrderLineService(orderLineDao, productDao);
        this.orderInvoiceService = new OrderInvoiceService(orderInvoiceDao, customerDao);
        this.customerDao = customerDao;
        this.employeeDao = employeeDao;
    }

    public Order findById(int orderId) throws DataAccessException {
        // Setup
        final Order order = new Order();

        // Get our order
        OrderDto orderDto = orderDao.findById(orderId);
        Employee employee = employeeDao.findById(orderDto.getEmployeeId());
        Customer customer = customerDao.findById(orderDto.getCustomerId());
        // Start populating the order
        order.setId(orderDto.getId());
        order.setDate(orderDto.getCreatedAt());
        order.setStatus(orderDto.getStatus());
        order.setCustomer(customer);
        order.setEmployee(employee);

        // Now find all our order lines for that order
        Set<OrderLine> orderLines = new HashSet<>(orderLineService.findAllByOrderId(orderId));
        order.setOrderLines(orderLines);

        // Get the invoices
        Set<OrderInvoice> orderInvoices = new HashSet<>(orderInvoiceService.findAllByOrderId(orderId));
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
