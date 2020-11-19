package persistence.connection;

import persistence.connection.mssql.MsSqlDataSource;
import persistence.connection.mssql.MsSqlRepositoryFactory;
import util.Config;

import java.io.IOException;

public class DataSourceManager {
    private static volatile DataSource connection;
    private static volatile PersistenceRepositoryFactory factory;
    private static volatile String dataSource;

    private static void loadSource() {
        if (dataSource == null) {
            synchronized(DataSourceManager.class) {
                if (dataSource == null) {
                    try {
                        dataSource = Config.getProperty("dataSource");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static DataSource getConnection() {
        if (connection == null) {
            synchronized(DataSourceManager.class) {
                if (connection == null) {
                    loadSource();

                    if (dataSource.equalsIgnoreCase("mssql"))
                        connection = new MsSqlDataSource();
                }
            }
        }

        return connection;
    }

    public static PersistenceRepositoryFactory getRepositoryFactory() {
        if (factory == null) {
            synchronized(DataSourceManager.class) {
                if (factory == null) {
                    loadSource();

                    if (dataSource.equalsIgnoreCase("mssql"))
                        factory = new MsSqlRepositoryFactory();
                }
            }
        }

        return factory;
    }
}
