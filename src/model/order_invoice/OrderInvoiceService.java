package model.order_invoice;

import exception.DataAccessException;
import model.order.Order;

public class OrderInvoiceService {
    private final OrderInvoiceDao orderInvoiceDao = new OrderInvoiceDaoMsSql();

    public OrderInvoice findById(int id) throws DataAccessException {
        return orderInvoiceDao.findById(id);
    }

    public void create(Order order, OrderInvoice orderInvoice) {
        orderInvoiceDao.create(order.getId(), orderInvoice.getCreatedAt(), orderInvoice.getDueDate(),
                orderInvoice.getToPay().getAmount(), orderInvoice.getHasPaid().getAmount());
    }
}
