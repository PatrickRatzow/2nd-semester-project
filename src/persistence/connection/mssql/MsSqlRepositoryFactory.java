package persistence.connection.mssql;

import persistence.connection.PersistenceRepositoryFactory;
import persistence.repository.OrderRepository;
import persistence.repository.ProductRepository;
import persistence.repository.mssql.MsSqlOrderRepository;
import persistence.repository.mssql.MsSqlProductRepository;

public class MsSqlRepositoryFactory implements PersistenceRepositoryFactory {
    @Override
    public ProductRepository createProductRepository() {
        return new MsSqlProductRepository();
    }

    @Override
    public OrderRepository createOrderRepository() {
        return new MsSqlOrderRepository();
    }
}
