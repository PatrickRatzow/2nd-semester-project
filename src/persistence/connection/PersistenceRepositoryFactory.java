package persistence.connection;

import persistence.repository.OrderRepository;
import persistence.repository.ProductRepository;

public interface PersistenceRepositoryFactory {
    ProductRepository createProductRepository();
    OrderRepository createOrderRepository();
}
