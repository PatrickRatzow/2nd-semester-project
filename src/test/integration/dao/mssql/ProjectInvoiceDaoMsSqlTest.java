package test.integration.dao.mssql;

import dao.ProjectInvoiceDao;
import dao.mssql.ProjectInvoiceDaoMsSql;
import datasource.DBConnection;
import datasource.DBManager;
import entity.*;
import exception.DataAccessException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProjectInvoiceDaoMsSqlTest {
    private static DBConnection connection;
    private static ProjectInvoiceDao dao;

    @BeforeAll
    static void setup() {
        connection = DBManager.getPool().getConnection();
        dao = new ProjectInvoiceDaoMsSql(connection);
    }

    @Test
    void testCanCreate() throws DataAccessException {
        // Arrange

        Project p = new Project();
        p.setId(4);

        ProjectInvoice projectInvoice = new ProjectInvoice();
        projectInvoice.setToPay(new Price(123));
        projectInvoice.setHasPaid(new Price(123543));
        projectInvoice.setDueDate(LocalDate.now());
        projectInvoice.setCreatedAt(LocalDateTime.now());
        projectInvoice.setId(p.getId());
        ProjectInvoice returnProjectInvoice;

        // Act
        returnProjectInvoice = dao.create(projectInvoice.getId(), projectInvoice);

        // Assert
        assertNotNull(returnProjectInvoice);
    }


    //This test works, but will pass, even though there is no project with the id.
    @Test
    void testCanUpdateProjectInvoice() throws DataAccessException {
        // Arrange
        int projectId = 1;
        ProjectInvoice projectInvoice = new ProjectInvoice();
        projectInvoice.setToPay(new Price(123));
        projectInvoice.setHasPaid(new Price(123));
        projectInvoice.setDueDate(LocalDate.now());
        projectInvoice.setCreatedAt(LocalDateTime.now());
        projectInvoice.setId(projectId);

        // Act
        dao.update(projectInvoice);


    }


    @AfterAll
    static void teardown() {
        connection.release();
    }
}
