package dao;

import exception.DataAccessException;
import exception.DataWriteException;
import entity.OrderInvoice;

public interface OrderInvoiceDao {
    OrderInvoice findById(int id) throws DataAccessException;
    OrderInvoice create(int orderId, OrderInvoice orderInvoice) throws DataWriteException;
}
