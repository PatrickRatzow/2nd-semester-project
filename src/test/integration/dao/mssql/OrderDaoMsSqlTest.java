package test.integration.dao.mssql;

import dao.OrderDao;
import dao.mssql.OrderDaoMsSql;
import datasource.DBConnection;
import datasource.DBManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OrderDaoMsSqlTest {
    private static DBConnection connection;
    private static OrderDao dao;

    @BeforeAll
    static void setup() {
        connection = DBManager.getPool().getConnection();
        dao = new OrderDaoMsSql(connection);
    }

    @Test
    void canFindById() {

    }

    @AfterAll
    static void teardown() {
        connection.release();
    }
}
