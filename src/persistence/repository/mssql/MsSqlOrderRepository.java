package persistence.repository.mssql;

import exception.DataAccessException;
import exception.DataWriteException;
import model.*;
import persistence.dao.OrderDao;
import persistence.dao.mssql.MsSqlOrderDao;
import persistence.repository.*;
import persistence.repository.mssql.dto.OrderDto;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class MsSqlOrderRepository implements OrderRepository {
    private final OrderDao orderDao = new MsSqlOrderDao();
    private final OrderLineRepository orderLineRepository = new MsSqlOrderLineRepository();
    private final OrderInvoiceRepository orderInvoiceRepository = new MsSqlOrderInvoiceRepository();
    private final CustomerRepository customerRepository = new MsSqlCustomerRepository();
    private final EmployeeRepository employeeRepository = new MsSqlEmployeeRepository();

    private Order buildObject(final OrderDto orderDto) throws DataAccessException {
        // Setup
        final Order order = new Order();
        order.setId(orderDto.getId());
        order.setDate(orderDto.getCreatedAt());
        order.setStatus(orderDto.getStatus());

        AtomicReference<DataAccessException> dataAccessException = new AtomicReference<>();
        Thread employeeThread = new Thread(() -> {
            try {
                final Employee employee = employeeRepository.findById(orderDto.getEmployeeId());
                order.setEmployee(employee);
            } catch (DataAccessException e) {
                dataAccessException.set(e);
            }
        });
        Thread customerThread = new Thread(() -> {
            try {
                final Customer customer = customerRepository.findById(orderDto.getCustomerId());
                order.setCustomer(customer);
            } catch (DataAccessException e) {
                dataAccessException.set(e);
            }
        });
        Thread orderLinesThread = new Thread(() -> {
            try {
                final Set<OrderLine> orderLines = new HashSet<>(orderLineRepository.findById(orderDto.getId()));
                order.setOrderLines(orderLines);
            } catch (DataAccessException e) {
                dataAccessException.set(e);
            }
        });
        Thread invoiceThread = new Thread(() -> {
            try {
                final OrderInvoice orderInvoice = orderInvoiceRepository.findById(orderDto.getId());
                order.setOrderInvoice(orderInvoice);
            } catch (DataAccessException e) {
                dataAccessException.set(e);
            }
        });

        employeeThread.start();
        customerThread.start();
        orderLinesThread.start();
        invoiceThread.start();

        try {
            employeeThread.join();
            customerThread.join();
            orderLinesThread.join();
            invoiceThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (dataAccessException.get() != null) {
            throw dataAccessException.get();
        }

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
        orderInvoiceRepository.create(order.getId(), orderInvoice);

        return order;
    }
}
