package dao;

import exception.DataAccessException;
import exception.DataWriteException;
import model.OrderInvoice;

public interface OrderInvoiceDao {
    OrderInvoice findById(int id) throws DataAccessException;
    OrderInvoice create(int orderId, OrderInvoice orderInvoice) throws DataWriteException;
}
