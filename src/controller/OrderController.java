package controller;

import datasource.DataSourceManager;
import exception.DataAccessException;
import model.Order;
import service.OrderService;

public class OrderController {
    private final OrderService repository = DataSourceManager.getRepositoryFactory().createOrderService();

    public Order findById(int id) throws DataAccessException {
        return repository.findById(id);
    }
}
