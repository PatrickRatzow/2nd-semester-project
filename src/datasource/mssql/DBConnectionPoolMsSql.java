package datasource.mssql;

import datasource.DBConnection;
import datasource.DBConnectionPool;
import util.JUnit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class DBConnectionPoolMsSql implements DBConnectionPool {
    private static final int POOL_SIZE = 15;
    private final BlockingQueue<DBConnection> pool = new ArrayBlockingQueue<>(POOL_SIZE);
    private final String user;
    private final String database;
    private static final String host = "hildur.ucn.dk";
    private static final int port = 1433;
    private static final String password = "Password1!";

    public DBConnectionPoolMsSql() {
        final boolean isJUnit = JUnit.isJUnitTest();
        user = isJUnit ? "dmaa0220_1083802" : "dmaa0220_1083750";
        database = user;

        List<Thread> threads = new LinkedList<>();
        for (int i = 0; i < POOL_SIZE; i++) {
            threads.add(new Thread(() -> {
            	DBConnectionMsSql conn = new DBConnectionMsSql(host, port, user, password, database);
            	pool.add(conn);
            }));
        }
        for (Thread t : threads) {
        	t.start();
        }
        for (Thread t : threads) {
        	try {
				t.join();
			} catch (InterruptedException e) {}
        }
        
        //if (!isJUnit) {
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
                        try {
                            final StringBuilder sql = new StringBuilder();
                            for (String line : Files.readAllLines(p)) {
                                sql.append(line).append("\n");
                            }
                            final PreparedStatement ps = conn.prepareStatement(sql.toString());
                            System.out.println("Running SQL script: " + p.getFileName());
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
    public DBConnection getConnection() {
        DBConnection conn = null;

        try {
            conn = pool.take();
            final DBConnection finalConn = conn;
            conn.setOnRelease(() -> releaseConnection(finalConn));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return conn;
    }

    private void releaseConnection(DBConnection connection) {
        try {
            pool.put(connection);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
