package service.mssql;

import dao.CustomerDao;
import dao.EmployeeDao;
import dao.OrderDao;
import dao.OrderInvoiceDao;
import dao.mssql.CustomerDaoMsSql;
import dao.mssql.EmployeeDaoMsSql;
import dao.mssql.OrderDaoMsSql;
import dao.mssql.OrderInvoiceDaoMsSql;
import dto.OrderDto;
import exception.DataAccessException;
import exception.DataWriteException;
import model.*;
import service.OrderLineService;
import service.OrderService;

import java.util.HashSet;
import java.util.Set;

public class OrderServiceMsSql implements OrderService {
    private final OrderDao orderDao = new OrderDaoMsSql();
    private final OrderLineService orderLineService = new OrderLineServiceMsSql();
    private final OrderInvoiceDao orderInvoiceDao = new OrderInvoiceDaoMsSql();
    private final CustomerDao customerDao = new CustomerDaoMsSql();
    private final EmployeeDao employeeDao = new EmployeeDaoMsSql();

    private Order buildObject(final OrderDto orderDto) throws DataAccessException {
        // Setup
        final Order order = new Order();
        order.setId(orderDto.getId());
        order.setDate(orderDto.getCreatedAt());
        order.setStatus(orderDto.getStatus());

        final Employee employee = employeeDao.findById(orderDto.getEmployeeId());
        order.setEmployee(employee);
        final Customer customer = customerDao.findById(orderDto.getCustomerId());
        order.setCustomer(customer);
        final Set<OrderLine> orderLines = new HashSet<>(orderLineService.findById(orderDto.getId()));
        order.setOrderLines(orderLines);
        final OrderInvoice orderInvoice = orderInvoiceDao.findById(orderDto.getId());
        order.setOrderInvoice(orderInvoice);

        return order;
    }

    @Override
    public Order findById(final int orderId) throws DataAccessException {
        return buildObject(orderDao.findById(orderId));
    }

    @Override
    public Order create(final Order order, final Project project) throws DataWriteException {
        // Create new order
        final Order newOrder = orderDao.create(order.getDate(), order.getStatus(), order.getCustomer().getId(),
                order.getEmployee().getId(), project.getId());
        // We only need the id from newOrder, so just merge it into the order parameter so we don't have to assemble it.
        order.setId(newOrder.getId());

        // Create all the order lines
        for (final OrderLine orderLine : order.getOrderLines()) {
            orderLineService.create(order.getId(), orderLine);
        }

        // Create invoice
        final OrderInvoice orderInvoice = order.getOrderInvoice();
        orderInvoiceDao.create(order.getId(), orderInvoice);

        return order;
    }
}
