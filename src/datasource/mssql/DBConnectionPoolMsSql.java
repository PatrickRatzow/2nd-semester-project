package datasource.mssql;

import datasource.DBConnection;
import datasource.DBConnectionOptions;
import datasource.DBConnectionPool;

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
    private final BlockingQueue<DBConnection> pool;

    public DBConnectionPoolMsSql(DBConnectionOptions options) {
        String host = options.getHost();
        String user = options.getUser();
        String password = options.getPassword();
        String database = options.getDatabase();
        int port = options.getPort();
        int poolSize = options.getPoolSize();
        
        pool = new ArrayBlockingQueue<>(poolSize);

        List<Thread> threads = new LinkedList<>();
        for (int i = 0; i < poolSize; i++) {
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
        
        //if (options.isTest()) {
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
