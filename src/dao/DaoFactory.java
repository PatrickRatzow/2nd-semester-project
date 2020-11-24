package dao;

// Might be redundant, can remove later
public interface DaoFactory {
    CustomerDao createCustomerDao();
    EmployeeDao createEmployeeDao();
    OrderDao createOrderDao();
    OrderInvoiceDao createOrderInvoiceDao();
    OrderLineDao createOrderLineDao();
    ProductCategoryDao createProductCategoryDao();
    ProductDao createProductDao();
    ProjectDao createProjectDao();
}
