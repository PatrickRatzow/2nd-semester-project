package service.mssql;

import service.OrderService;
import service.ProjectService;
import service.ServiceFactory;

public class RepositoryFactoryMsSql implements ServiceFactory {
    @Override
    public OrderService createOrderService() {
        return new OrderServiceMsSql();
    }

    @Override
    public ProjectService createProjectService() {
        return new ProjectServiceMsSql();
    }
}
