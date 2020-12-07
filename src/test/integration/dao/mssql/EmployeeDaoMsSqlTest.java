package test.integration.dao.mssql;

import dao.EmployeeDao;
import dao.mssql.DaoFactoryMsSql;
import datasource.DBConnection;
import datasource.DBManager;
import entity.Employee;
import exception.DataAccessException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EmployeeDaoMsSqlTest {
    private static DBConnection connection;
    private static EmployeeDao dao;

    @BeforeAll
    static void setup() {
        connection = DBManager.getPool().getConnection();
        try {
            connection.startTransaction();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        dao = new DaoFactoryMsSql().createEmployeeDao(connection);
    }
    
    @Test
    void canFindById() throws DataAccessException {
        // Arrange
        Employee employee = null;
        
        // Act
        employee = dao.findById(1);
        
        // Assert
        assertNotNull(employee);
    }

    @Test
    void cannotFindById() throws DataAccessException {
        // Arrange
        Employee employee = null;

        // Act
        employee = dao.findById(10);

        // Assert
        assertNull(employee);
    }
    
    @AfterAll
    static void teardown() {
        try {
            connection.rollbackTransaction();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connection.release();
    }
}
