package util;

import datasource.DBConnection;
import datasource.DBManager;

import java.util.function.Consumer;

public class ConnectionThread extends Thread {
    private final Consumer<DBConnection> callback;

    public ConnectionThread(Consumer<DBConnection> callback) {
        this.callback = callback;
    }

    @Override
    public void run() {
        DBConnection conn = DBManager.getPool().getConnection();

        callback.accept(conn);

        if (conn != null)
            conn.release();
    }
}
