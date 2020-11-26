package datasource.mssql;

import datasource.DBConnection;
import datasource.DBConnectionPool;
import util.JUnit;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DBConnectionPoolMsSql implements DBConnectionPool {
    private final List<DBConnection> pool = Collections.synchronizedList(new LinkedList<>());
    private final List<DBConnection> usedConnections = Collections.synchronizedList(new LinkedList<>());
    private static final int POOL_SIZE = 15;
    private final String user;
    private final String database;
    private static final String host = "hildur.ucn.dk";
    private static final int port = 1433;
    private static final String password = "Password1!";

    public DBConnectionPoolMsSql() {
        final boolean isJUnit = JUnit.isJUnitTest();
        user = isJUnit ? "dmaa0220_1083750" : "dmaa0220_1083802";
        database = user;

        for (int i = 0; i < POOL_SIZE; i++) {
            pool.add(new DBConnectionMsSql(host, port, user, password, database));
        }
    }

    @Override
    public synchronized DBConnection getConnection() {
        DBConnection conn = pool.remove(pool.size() - 1);
        usedConnections.add(conn);

        conn.setOnRelease(() -> releaseConnection(conn));

        return conn;
    }

    private synchronized void releaseConnection(DBConnection connection) {
        pool.add(connection);
        usedConnections.remove(connection);
    }
}
