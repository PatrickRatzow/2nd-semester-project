package controller;

import exception.DataAccessException;
import model.Order;
import persistence.connection.PersistenceConnectionManager;
import persistence.connection.PersistenceRepositoryFactory;
import persistence.repository.OrderRepository;

public class OrderController {
    private final OrderRepository repository;

    public OrderController() {
        PersistenceRepositoryFactory repositoryFactory = PersistenceConnectionManager.getRepositoryFactory();

        repository = repositoryFactory.createOrderRepository();
    }

    public Order findById(int id) throws DataAccessException {
        return repository.findById(id);
    }
}
