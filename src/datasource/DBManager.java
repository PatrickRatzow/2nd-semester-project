package datasource;

import datasource.mssql.DBConnectionPoolMsSql;
import util.Config;

import java.util.function.Consumer;

public class DBManager {
    private volatile static DBManager instance;
    private volatile DBConnectionPool pool;
    private volatile DBConnectionOptions options;

    private DBManager() {
        loadOptions();
    }
    
    private void loadOptions() {
        if (options == null) {
            synchronized (this) {
                if (options == null) {
                    options = Config.getInstance().getDatabaseOptions();
                }
            }
        }
    }

    public DBConnectionPool getPool() {
        if (pool == null) {
            synchronized (this) {
                if (pool == null) {
                    loadOptions();
    
                    if (options.getDataSource().equalsIgnoreCase("mssql")) {
                        pool = new DBConnectionPoolMsSql(options);
                    }
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
