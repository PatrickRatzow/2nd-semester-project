package datasource.mssql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import datasource.DataSource;
import service.ServiceFactory;
import service.mssql.RepositoryFactoryMsSql;
import util.JUnit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

/**
 * The type Db connection.
 */
public class DataSourceMsSql implements DataSource {
    private static HikariDataSource ds;
    private static Connection connection;
    private static final DataSourceMsSql persistenceConnection = new DataSourceMsSql();
    private final RepositoryFactoryMsSql repositoryFactory = new RepositoryFactoryMsSql();

    public DataSourceMsSql() {
        final boolean isJUnit = JUnit.isJUnitTest();
        final String name = isJUnit ? "dmaa0220_1083750" : "dmaa0220_1083802";

        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        config.setMaximumPoolSize(500);
        config.setConnectionTestQuery("SELECT GETDATE()");
        config.setJdbcUrl("jdbc:sqlserver://hildur.ucn.dk:1433");
        config.addDataSourceProperty("databaseName", name);
        config.addDataSourceProperty("user", name);
        config.addDataSourceProperty("password", "Password1!");
        ds = new HikariDataSource(config);

        try {
            connection = ds.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //if (isJUnit) {
        setupDatabase();
        //}
    }


    private void setupDatabase() {
        try {
            startTransaction();

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
                        final PreparedStatement ps = prepareStatement(sql.toString());
                        System.out.println(p.getFileName());
                        ps.execute();
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
                });

            commitTransaction();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static DataSourceMsSql getInstance() {
        return persistenceConnection;
    }

    /**
     * Start transaction.
     *
     * @throws SQLException the sql exception
     */
    public void startTransaction(Connection conn) throws SQLException {
        conn.setAutoCommit(false);
    }

    public void startTransaction() throws SQLException {
        startTransaction(connection);
    }

    public Savepoint setSavepoint(Connection conn) throws SQLException {
        return conn.setSavepoint();
    }

    public Savepoint setSavepoint() throws SQLException {
        return setSavepoint(connection);
    }

    /**
     * Commit transaction.
     *
     * @throws SQLException the sql exception
     */
    public void commitTransaction(Connection conn) throws SQLException {
        conn.commit();
        conn.setAutoCommit(true);
    }

    public void commitTransaction() throws SQLException {
        commitTransaction(connection);
    }

    /**
     * Rollback transaction.
     *
     * @throws SQLException the sql exception
     */
    public void rollbackTransaction(Connection conn) throws SQLException {
        conn.rollback();
        conn.setAutoCommit(true);
    }

    public void rollbackTransaction() throws SQLException {
        rollbackTransaction(connection);
    }

    public void rollbackTransaction(Connection conn, Savepoint savepoint) throws SQLException {
        conn.rollback(savepoint);
        conn.setAutoCommit(true);
    }

    public void rollbackTransaction(Savepoint savepoint) throws SQLException {
        rollbackTransaction(connection, savepoint);
    }


    /**
     * Gets connection.
     *
     * @return the connection
     */
    @Override
    public Connection getConnection() {
        return connection;
    }

    /**
     * Prepare statement prepared statement.
     *
     * @param sql the sql
     * @return the prepared statement
     * @throws SQLException the sql exception
     */
    public PreparedStatement prepareStatement(Connection conn, String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return prepareStatement(connection, sql);
    }

    public CallableStatement prepareCall(Connection conn, String sql) throws SQLException {
        return conn.prepareCall(sql);
    }

    public CallableStatement prepareCall(String sql) throws SQLException {
        return prepareCall(connection, sql);
    }

    /**
     * Prepare statement prepared statement.
     *
     * @param sql               the sql
     * @param autoGeneratedKeys the auto generated keys
     * @return the prepared statement
     * @throws SQLException the sql exception
     */
    public PreparedStatement prepareStatement(Connection conn, String sql, int autoGeneratedKeys) throws SQLException {
        return conn.prepareStatement(sql, autoGeneratedKeys);
    }

    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        return prepareStatement(connection, sql, autoGeneratedKeys);
    }

    /**
     * Disconnect.
     */
    public void disconnect(Connection conn) throws SQLException {
        conn.close();
    }

    @Override
    public ServiceFactory getRepositoryFactory() {
        return repositoryFactory;
    }
}