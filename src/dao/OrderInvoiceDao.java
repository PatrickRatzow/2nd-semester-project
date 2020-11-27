package dao;

import entity.OrderInvoice;
import exception.DataAccessException;
import exception.DataWriteException;

public interface OrderInvoiceDao {
    OrderInvoice findByOrderId(int id) throws DataAccessException;
    OrderInvoice create(int orderId, OrderInvoice orderInvoice) throws DataWriteException;
}
