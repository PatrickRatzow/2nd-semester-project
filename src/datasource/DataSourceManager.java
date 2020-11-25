package datasource;

import datasource.mssql.DataSourceMsSql;
import service.ServiceFactory;
import service.mssql.ServiceFactoryMsSql;

public class DataSourceManager {
    private static volatile DataSource connection;
    private static volatile ServiceFactory factory;
    private static volatile String dataSource;

    private static void loadSource() {
        if (dataSource == null) {
            synchronized(DataSourceManager.class) {
                if (dataSource == null) {
                    dataSource = "mssql";//Config.getProperty("dataSource");
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
    public static ServiceFactory getServiceFactory() {
        if (factory == null) {
            synchronized(DataSourceManager.class) {
                if (factory == null) {
                    loadSource();

                    if (dataSource.equalsIgnoreCase("mssql"))
                        factory = new ServiceFactoryMsSql();
                }
            }
        }

        return factory;
    }
}
