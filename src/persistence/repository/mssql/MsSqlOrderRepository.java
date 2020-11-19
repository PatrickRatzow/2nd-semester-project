package persistence.repository.mssql;

import exception.DataAccessException;
import exception.DataWriteException;
import model.*;
import persistence.dao.CustomerDao;
import persistence.dao.EmployeeDao;
import persistence.dao.OrderDao;
import persistence.dao.OrderInvoiceDao;
import persistence.dao.mssql.MsSqlCustomerDao;
import persistence.dao.mssql.MsSqlEmployeeDao;
import persistence.dao.mssql.MsSqlOrderDao;
import persistence.dao.mssql.MsSqlOrderInvoiceDao;
import persistence.repository.OrderLineRepository;
import persistence.repository.OrderRepository;
import persistence.repository.mssql.dto.OrderDto;

import java.util.HashSet;
import java.util.Set;

public class MsSqlOrderRepository implements OrderRepository {
    private final OrderDao orderDao = new MsSqlOrderDao();
    private final OrderLineRepository orderLineRepository = new MsSqlOrderLineRepository();
    private final OrderInvoiceDao orderInvoiceDao = new MsSqlOrderInvoiceDao();
    private final CustomerDao customerDao = new MsSqlCustomerDao();
    private final EmployeeDao employeeDao = new MsSqlEmployeeDao();

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
        final Set<OrderLine> orderLines = new HashSet<>(orderLineRepository.findById(orderDto.getId()));
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
            orderLineRepository.create(order.getId(), orderLine);
        }

        // Create invoice
        final OrderInvoice orderInvoice = order.getOrderInvoice();
        orderInvoiceDao.create(order.getId(), orderInvoice);

        return order;
    }
}
