package services;

import database.CustomerDao;
import database.DataAccessException;
import database.OrderInvoiceDao;
import model.Order;
import model.OrderInvoice;

import java.util.List;

public class OrderInvoiceService {
    private final OrderInvoiceDao orderInvoiceDao;

    public OrderInvoiceService(OrderInvoiceDao orderInvoiceDao, CustomerDao customerDao) {
        this.orderInvoiceDao = orderInvoiceDao;
    }

    public List<OrderInvoice> findAllByOrderId(int orderId) throws DataAccessException {
        List<OrderInvoice> orderInvoices = orderInvoiceDao.findAllByOrderId(orderId);

        return orderInvoices;
    }

    public void create(Order order, OrderInvoice orderInvoice) {
        orderInvoiceDao.create(order.getId(), orderInvoice.getCreatedAt(), orderInvoice.getDueDate(),
                orderInvoice.getToPay().getAmount(), orderInvoice.getHasPaid().getAmount());
    }
}
