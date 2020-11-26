package datasource;

public interface DBConnectionPool {
    DBConnection getConnection();
}