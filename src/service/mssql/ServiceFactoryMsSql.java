package service.mssql;

import service.OrderService;
import service.ProductService;
import service.ProjectService;
import service.ServiceFactory;

public class ServiceFactoryMsSql implements ServiceFactory {
    @Override
    public OrderService createOrderService() {
        return new OrderServiceMsSql();
    }

    @Override
    public ProjectService createProjectService() {
        return new ProjectServiceMsSql();
    }

    @Override
    public ProductService createProductService() {
        return new ProductServiceMsSql();
    }
}
