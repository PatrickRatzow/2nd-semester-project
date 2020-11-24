package dao.mssql;

import dao.*;

public class DaoFactoryMsSql implements DaoFactory {
    @Override
    public CustomerDao createCustomerDao() {
        return new CustomerDaoMsSql();
    }

    @Override
    public EmployeeDao createEmployeeDao() {
        return new EmployeeDaoMsSql();
    }

    @Override
    public OrderDao createOrderDao() {
        return new OrderDaoMsSql();
    }

    @Override
    public OrderInvoiceDao createOrderInvoiceDao() {
        return new OrderInvoiceDaoMsSql();
    }

    @Override
    public OrderLineDao createOrderLineDao() {
        return new OrderLineDaoMsSql();
    }

    @Override
    public ProductCategoryDao createProductCategoryDao() {
        return new ProductCategoryDaoMsSql();
    }

    @Override
    public ProductDao createProductDao() {
        return new ProductDaoMsSql();
    }

    @Override
    public ProjectDao createProjectDao() {
        return new ProjectDaoMsSql();
    }
}
