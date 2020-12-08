package dao.mssql;

import dao.*;
import datasource.DBConnection;

public class DaoFactoryMsSql implements DaoFactory {
    private DBConnection connection;
    public DaoFactoryMsSql(DBConnection connection) {
        this.connection = connection;
    }
    
    @Override
    public CustomerDao createCustomerDao() {
        return new CustomerDaoMsSql(connection);
    }

    @Override
    public EmployeeDao createEmployeeDao() {
        return new EmployeeDaoMsSql(connection);
    }

    @Override
    public OrderDao createOrderDao() {
        return new OrderDaoMsSql(connection);
    }

    @Override
    public OrderLineDao createOrderLineDao() {
        return new OrderLineDaoMsSql(connection);
    }
    
    @Override
    public ProductDao createProductDao() {
        return new ProductDaoMsSql(connection);
    }

    @Override
    public ProjectDao createProjectDao() {
        return new ProjectDaoMsSql(connection);
    }

    @Override
    public ProjectInvoiceDao createProjectInvoiceDao() {
        return new ProjectInvoiceDaoMsSql(connection);
    }
}
