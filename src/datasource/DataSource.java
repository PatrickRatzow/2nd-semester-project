package datasource;

import service.ServiceFactory;

import java.sql.Connection;

public interface DataSource {
    Connection getConnection();
    ServiceFactory getRepositoryFactory();
}
