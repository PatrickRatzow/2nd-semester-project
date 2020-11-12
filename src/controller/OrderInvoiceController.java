package controller;

import database.DataAccessException;
import database.OrderInvoiceDao;
import database.OrderInvoiceDaoMsSql;
import model.Order;
import model.OrderInvoice;

import java.util.List;

public class OrderInvoiceController {
    private final OrderInvoiceDao orderInvoiceDao = new OrderInvoiceDaoMsSql();

    public List<OrderInvoice> findAllByOrderId(int orderId) throws DataAccessException {
        List<OrderInvoice> orderInvoices = orderInvoiceDao.findAllByOrderId(orderId);

        return orderInvoices;
    }

    public void create(Order order, OrderInvoice orderInvoice) {
        orderInvoiceDao.create(order.getId(), orderInvoice.getCreatedAt(), orderInvoice.getDueDate(),
                orderInvoice.getToPay().getAmount(), orderInvoice.getHasPaid().getAmount());
    }
}
