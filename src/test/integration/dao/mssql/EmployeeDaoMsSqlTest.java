package test.integration.dao.mssql;

import dao.EmployeeDao;
import datasource.DBConnection;
import datasource.DBManager;
import datasource.DataAccessException;
import model.Employee;
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
        connection = DBManager.getInstance().getPool().getConnection();
        try {
            connection.startTransaction();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        dao = connection.getDaoFactory().createEmployeeDao();
    }
    
    @Test
    void testCanFindById() throws DataAccessException {
        // Arrange
        Employee employee;
        
        // Act
        employee = dao.findById(1);
        
        // Assert
        assertNotNull(employee);
    }

    @Test
    void testCannotFindById() throws DataAccessException {
        // Arrange
        Employee employee;

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
