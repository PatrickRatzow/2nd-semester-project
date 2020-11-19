package persistence.connection.mssql;

import persistence.connection.PersistenceRepositoryFactory;
import persistence.repository.OrderRepository;
import persistence.repository.ProjectRepository;
import persistence.repository.mssql.MsSqlOrderRepository;
import persistence.repository.mssql.MsSqlProjectRepository;

public class MsSqlRepositoryFactory implements PersistenceRepositoryFactory {
    @Override
    public OrderRepository createOrderRepository() {
        return new MsSqlOrderRepository();
    }

    @Override
    public ProjectRepository createProjectRepository() {
        return new MsSqlProjectRepository();
    }
}
