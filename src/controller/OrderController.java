package controller;

import exception.DataAccessException;
import model.Order;
import persistence.connection.DataSourceManager;
import persistence.repository.OrderRepository;

public class OrderController {
    private final OrderRepository repository = DataSourceManager.getRepositoryFactory().createOrderRepository();

    public Order findById(int id) throws DataAccessException {
        return repository.findById(id);
    }
}
