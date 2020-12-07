package dao;

import exception.DataAccessException;
import model.ProjectInvoice;

public interface ProjectInvoiceDao {
    ProjectInvoice findById(int id) throws DataAccessException;

    ProjectInvoice create(ProjectInvoice projectInvoice) throws DataAccessException;

    void update(ProjectInvoice projectInvoice) throws DataAccessException;
}
