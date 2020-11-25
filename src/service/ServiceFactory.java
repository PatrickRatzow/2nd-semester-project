package service;

public interface ServiceFactory {
    OrderService createOrderService();
    ProjectService createProjectService();
    ProductService createProductService();
}
