package persistence.connection;

import java.sql.Connection;

public interface DataSource {
    Connection getConnection();
    PersistenceRepositoryFactory getRepositoryFactory();
}
