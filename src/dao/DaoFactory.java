package dao;

import datasource.DBConnection;

public interface DaoFactory {
    CustomerDao createCustomerDao(DBConnection connection);
    EmployeeDao createEmployeeDao(DBConnection connection);
    OrderDao createOrderDao(DBConnection connection);
    OrderLineDao createOrderLineDao(DBConnection connection);
    ProductCategoryDao createProductCategoryDao(DBConnection connection);
    ProductDao createProductDao(DBConnection connection);
    ProjectDao createProjectDao(DBConnection connection);
    ProjectInvoiceDao createProjectInvoiceDao(DBConnection connection);
}
