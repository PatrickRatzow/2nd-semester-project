package dao.mssql;

import dao.*;
import datasource.DBConnection;

public class DaoFactoryMsSql implements DaoFactory {
    @Override
    public CustomerDao createCustomerDao(DBConnection connection) {
        return new CustomerDaoMsSql(connection);
    }

    @Override
    public EmployeeDao createEmployeeDao(DBConnection connection) {
        return new EmployeeDaoMsSql(connection);
    }

    @Override
    public OrderDao createOrderDao(DBConnection connection) {
        return new OrderDaoMsSql(connection);
    }

    @Override
    public OrderInvoiceDao createOrderInvoiceDao(DBConnection connection) {
        return new OrderInvoiceDaoMsSql(connection);
    }

    @Override
    public OrderLineDao createOrderLineDao(DBConnection connection) {
        return new OrderLineDaoMsSql(connection);
    }

    @Override
    public ProductCategoryDao createProductCategoryDao(DBConnection connection) {
        return new ProductCategoryDaoMsSql(connection);
    }

    @Override
    public ProductDao createProductDao(DBConnection connection) {
        return new ProductDaoMsSql(connection);
    }

    @Override
    public ProjectDao createProjectDao(DBConnection connection) {
        return new ProjectDaoMsSql(connection);
    }
}
