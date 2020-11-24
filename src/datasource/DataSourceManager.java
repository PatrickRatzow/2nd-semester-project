package datasource;

import datasource.mssql.DataSourceMsSql;
import service.ServiceFactory;
import util.Config;

import java.io.IOException;

public class DataSourceManager {
    private static volatile DataSource connection;
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
                        connection = new DataSourceMsSql();
                }
            }
        }

        return connection;
    }

    // Just a wrapper
    public static ServiceFactory getRepositoryFactory() {
        return getConnection().getRepositoryFactory();
    }
}
