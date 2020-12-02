package dao;

import entity.ProjectInvoice;
import exception.DataAccessException;

public interface ProjectInvoiceDao {
    ProjectInvoice findByOrderId(int id) throws DataAccessException;
    ProjectInvoice create(int orderId, ProjectInvoice projectInvoice) throws DataAccessException;
    void update(ProjectInvoice projectInvoice) throws DataAccessException;
}
