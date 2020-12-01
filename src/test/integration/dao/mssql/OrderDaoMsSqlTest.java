package test.integration.dao.mssql;

import dao.OrderDao;
import dao.mssql.OrderDaoMsSql;
import datasource.DBConnection;
import datasource.DBManager;
import entity.Order;
import entity.OrderStatus;
import exception.DataAccessException;
import exception.DataWriteException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

public class OrderDaoMsSqlTest {
    private static DBConnection connection;
    private static OrderDao dao;

    @BeforeAll
    static void setup() {
        connection = DBManager.getPool().getConnection();
        dao = new OrderDaoMsSql(connection);
    }

    @Test
    void canFindById() throws DataAccessException {
        // Arrange
        Order order = null;

        // Act
        order = dao.findById(1, true);

        // Assert
        assertNotNull(order);
    }
    
    
    @Test
    void create() throws DataWriteException {
    	//Arrange

    	Order order = null;
    	
    	//Act
    	
    	order = dao.create(LocalDateTime.now(), OrderStatus.AWAITING, 1, 1, 1);
    	
    	//Assert
    	assertNotNull(order);
    }

    @AfterAll
    static void teardown() {
        connection.release();
    }
}
