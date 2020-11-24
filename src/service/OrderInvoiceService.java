package service;

import exception.DataAccessException;
import exception.DataWriteException;
import model.OrderInvoice;

public interface OrderInvoiceService {
    OrderInvoice findById(int id) throws DataAccessException;
    OrderInvoice create(int id, OrderInvoice orderInvoice) throws DataWriteException;
}
