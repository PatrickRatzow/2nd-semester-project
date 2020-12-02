package dao;

import entity.ProjectInvoice;
import exception.DataAccessException;

public interface ProjectInvoiceDao {
    ProjectInvoice findById(int id) throws DataAccessException;
    ProjectInvoice create(ProjectInvoice projectInvoice) throws DataAccessException;
    void update(ProjectInvoice projectInvoice) throws DataAccessException;
}
