package datasource;

import dao.DaoFactory;
import datasource.mssql.DBConnectionPoolMsSql;

import java.util.function.Consumer;

public class DBManager {
    private static DBManager instance;
    private volatile DBConnectionPool pool;
    private volatile String dataSource;
    private volatile DaoFactory factory;

    private DBManager() {
        loadSource();
    }
    
    private void loadSource() {
        if (dataSource == null) {
            synchronized (this) {
                if (dataSource == null) {
                    dataSource = "mssql";//Config.getProperty("dataSource");
                }
            }
        }
    }

    public DBConnectionPool getPool() {
        if (pool == null) {
            synchronized (this) {
                if (pool == null) {
                    loadSource();

                    if (dataSource.equalsIgnoreCase("mssql"))
                        pool = new DBConnectionPoolMsSql();
                }
            }
        }

        return pool;
    }

    public Thread getConnectionThread(Consumer<DBConnection> callback) {
        return new Thread(() -> {
            DBConnection connection = getPool().getConnection();
            
            callback.accept(connection);
            
            if (connection != null) {
                connection.release();
            }
        });
    }
    
    public static DBManager getInstance() {
        if (instance == null) {
            synchronized (DBManager.class) {
                if (instance == null) {
                    instance = new DBManager();
                }
            }
        }
        
        return instance;
    }
}
