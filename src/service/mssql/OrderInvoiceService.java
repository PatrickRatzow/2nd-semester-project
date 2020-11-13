package service.mssql;

import exception.DataAccessException;
import model.Order;
import model.OrderInvoice;
import persistance.OrderInvoiceDao;
import persistance.mssql.OrderInvoiceDaoMsSql;

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
