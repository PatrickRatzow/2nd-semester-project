package persistence.connection;

import persistence.repository.OrderRepository;
import persistence.repository.ProjectRepository;

public interface PersistenceRepositoryFactory {
    OrderRepository createOrderRepository();
    ProjectRepository createProjectRepository();
}
