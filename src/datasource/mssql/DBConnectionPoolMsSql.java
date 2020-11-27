package datasource.mssql;

import datasource.DBConnection;
import datasource.DBConnectionPool;
import util.JUnit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

        //if (isJUnit) {
            setupDatabase();
        //}
    }

    private void setupDatabase() {
        DBConnection conn = getConnection();

        try {
            conn.startTransaction();

            Files.walk(Paths.get("./sql/"))
                    .sorted()
                    /* Only allow SQL files */
                    .filter(p -> p.getFileName().toString().endsWith(".sql"))
                    /* Execute each script sequentially to ensure that everything gets created correctly */
                    .forEach(p -> {
                        System.out.println(p.getFileName().toAbsolutePath());

                        try {
                            final StringBuilder sql = new StringBuilder();
                            for (String line : Files.readAllLines(p)) {
                                sql.append(line).append("\n");
                            }
                            final PreparedStatement ps = conn.prepareStatement(sql.toString());
                            System.out.println(p.getFileName());
                            ps.execute();
                        } catch (IOException | SQLException e) {
                            e.printStackTrace();
                        }
                    });

            conn.commitTransaction();
        } catch (SQLException | IOException e) {
            try {
                conn.rollbackTransaction();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } finally {
            conn.release();
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
