package dao;

public interface DaoFactory {
    CustomerDao createCustomerDao();

    EmployeeDao createEmployeeDao();

    OrderDao createOrderDao();

    OrderLineDao createOrderLineDao();
    
    ProductDao createProductDao();

    ProjectDao createProjectDao();

    ProjectInvoiceDao createProjectInvoiceDao();
}
