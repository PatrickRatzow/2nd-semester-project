package test.integration.dao.mssql;

import dao.ProjectInvoiceDao;
import dao.mssql.ProjectInvoiceDaoMsSql;
import datasource.DBConnection;
import datasource.DBManager;
import entity.Price;
import entity.ProjectInvoice;
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
        //Arrange
        ProjectInvoice projectInvoice = new ProjectInvoice();
        projectInvoice.setToPay(new Price(123));
        projectInvoice.setHasPaid(new Price(123));
        projectInvoice.setDueDate(LocalDate.now());
        projectInvoice.setCreatedAt(LocalDateTime.now());
        projectInvoice.setId(3);
        ProjectInvoice returnProjectInvoice;

        //Act
        returnProjectInvoice = dao.create(projectInvoice.getId(), projectInvoice);

        //Assert
        assertNotNull(returnProjectInvoice);
    }

    @Test
    void testCanUpdateProjectInvoice() throws DataAccessException {
        // Arrange
        int projectId = 5;
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
