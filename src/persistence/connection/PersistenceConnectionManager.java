package persistence.connection;

import persistence.connection.mssql.MsSqlPersistenceConnection;

public class PersistenceConnectionManager {
    private static volatile PersistenceConnection connection;

    public static PersistenceConnection getConnection() {
        if (connection == null) {
            synchronized(PersistenceConnection.class) {
                if (connection == null) {
                    // TODO: Make this choose via some config idk
                    connection = new MsSqlPersistenceConnection();
                }
            }
        }

        return connection;
    }

    public static PersistenceRepositoryFactory getRepositoryFactory() {
        return getConnection().getRepositoryFactory();
    }
}
