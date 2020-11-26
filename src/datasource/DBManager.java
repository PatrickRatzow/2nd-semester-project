package datasource;

import dao.DaoFactory;
import dao.mssql.DaoFactoryMsSql;
import datasource.mssql.DBConnectionPoolMsSql;

public class DBManager {
    private static volatile DBConnectionPool pool;
    private static volatile String dataSource;
    private static volatile DaoFactory factory;

    private static void loadSource() {
        if (dataSource == null) {
            synchronized(DBManager.class) {
                if (dataSource == null) {
                    dataSource = "mssql";//Config.getProperty("dataSource");
                }
            }
        }
    }

    public static DBConnectionPool getPool() {
        if (pool == null) {
            synchronized(DBManager.class) {
                if (pool == null) {
                    loadSource();

                    if (dataSource.equalsIgnoreCase("mssql"))
                        pool = new DBConnectionPoolMsSql();
                }
            }
        }

        return pool;
    }

    public static DaoFactory getDaoFactory() {
        if (factory == null) {
            synchronized(DBManager.class) {
                if (factory == null) {
                    loadSource();

                    if (dataSource.equalsIgnoreCase("mssql"))
                        factory = new DaoFactoryMsSql();
                }
            }
        }

        return factory;
    }
}
