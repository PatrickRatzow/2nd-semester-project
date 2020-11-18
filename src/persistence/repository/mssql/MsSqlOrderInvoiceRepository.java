package persistence.repository.mssql;

import exception.DataAccessException;
import exception.DataWriteException;
import model.OrderInvoice;
import persistence.dao.OrderInvoiceDao;
import persistence.dao.mssql.MsSqlOrderInvoiceDao;
import persistence.repository.OrderInvoiceRepository;

public class MsSqlOrderInvoiceRepository implements OrderInvoiceRepository {
    private final OrderInvoiceDao orderInvoiceDao = new MsSqlOrderInvoiceDao();

    public OrderInvoice findById(final int orderId) throws DataAccessException {
        return orderInvoiceDao.findById(orderId);
    }

    public OrderInvoice create(final int orderId, final OrderInvoice orderInvoice) throws DataWriteException {
        return orderInvoiceDao.create(orderId, orderInvoice);
    }
}
