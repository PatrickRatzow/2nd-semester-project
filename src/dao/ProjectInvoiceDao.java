package dao;

import entity.ProjectInvoice;
import exception.DataAccessException;
import exception.DataWriteException;

public interface ProjectInvoiceDao {
    ProjectInvoice findByOrderId(int id) throws DataAccessException;
    ProjectInvoice create(int orderId, ProjectInvoice projectInvoice) throws DataWriteException;
}
