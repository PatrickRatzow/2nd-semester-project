package test.integration.dao.mssql;

import dao.ProjectInvoiceDao;
import dao.mssql.ProjectInvoiceDaoMsSql;
import datasource.DBConnection;
import datasource.DBManager;
import datasource.DataAccessException;
import model.Price;
import model.ProjectInvoice;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProjectInvoiceDaoMsSqlTest {
    private static DBConnection connection;
    private static ProjectInvoiceDao dao;

    @BeforeAll
    static void setup() {
        connection = DBManager.getPool().getConnection();
        dao = new ProjectInvoiceDaoMsSql(connection);
    }

    @Test
    void testCanCreateIfTheresAProjectWithEqualId() throws DataAccessException {
        // Arrange
        int projectId = 4;
        ProjectInvoice projectInvoice = new ProjectInvoice();
        projectInvoice.setToPay(new Price(100 * 100));
        projectInvoice.setHasPaid(new Price(0));
        projectInvoice.setDueDate(LocalDate.now());
        projectInvoice.setCreatedAt(LocalDateTime.now());
        projectInvoice.setId(projectId);
        ProjectInvoice returnProjectInvoice;

        // Act
        returnProjectInvoice = dao.create(projectInvoice);

        // Assert
        assertNotNull(returnProjectInvoice);
    }

    @Test
    void testCantCreateIfTheresNoProjectWithEqualId() {
        // Arrange
        int projectId = 50000;
        ProjectInvoice projectInvoice = new ProjectInvoice();
        projectInvoice.setToPay(new Price(100 * 100));
        projectInvoice.setHasPaid(new Price(0));
        projectInvoice.setDueDate(LocalDate.now());
        projectInvoice.setCreatedAt(LocalDateTime.now());
        projectInvoice.setId(projectId);

        // Act
        assertThrows(DataAccessException.class, () -> dao.create(projectInvoice));
    }

    @Test
    void testCanUpdateProjectInvoice() throws DataAccessException {
        // Arrange
        int projectId = 1;
        ProjectInvoice projectInvoice = new ProjectInvoice();
        projectInvoice.setToPay(new Price(100 * 100));
        projectInvoice.setId(projectId);

        // Act
        dao.update(projectInvoice);
    }


    @AfterAll
    static void teardown() {
        connection.release();
    }
}
