package service;

import exception.DataAccessException;
import exception.DataWriteException;
import entity.OrderInvoice;

public interface OrderInvoiceService {
    OrderInvoice findById(int id) throws DataAccessException;
    OrderInvoice create(int id, OrderInvoice orderInvoice) throws DataWriteException;
}
