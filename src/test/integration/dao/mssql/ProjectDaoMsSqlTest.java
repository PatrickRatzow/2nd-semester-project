package test.integration.dao.mssql;

import dao.ProjectDao;
import dao.mssql.ProjectDaoMsSql;
import datasource.DBConnection;
import datasource.DBManager;
import exception.DataAccessException;
import model.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectDaoMsSqlTest {
    private static DBConnection connection;
    private static ProjectDao dao;

    @BeforeAll
    static void setup() {
        connection = DBManager.getPool().getConnection();
        dao = new ProjectDaoMsSql(connection);
    }

    @Test
    void testCanFindAllExpectedProjects() throws DataAccessException {
        // Arrange
        List<Project> projects;

        // Act
        projects = dao.findAll(false);

        // Assert
        assertEquals(projects.size(), 4);
    }

    @Test
    void testCanFindById() throws DataAccessException {
        //Arrange
        Project project;

        //act
        project = dao.findById(3, true);

        //assert
        assertNotNull(project);
    }

    @Test
    void testCantFindByIdIfItDoesntExistInDatabase() throws DataAccessException {
        //Arrange
        Project project;

        //act
        project = dao.findById(99999999, false);

        //assert
        assertNull(project);
    }

    @Test
    void testCanFindByName() throws DataAccessException {
        //Arrange
        String name = "The Project";
        List<Project> projects;

        //act
        projects = dao.findByName(name, false);

        //assert
        assertEquals(projects.size(), 1);
    }

    @Test
    void testCantFindByNameIfItDoesntExistInDatabase() throws DataAccessException {
        //Arrange
        String name = "Egon Olsens vinduer";
        List<Project> projects;

        //act
        projects = dao.findByName(name, false);

        //assert
        assertNotEquals(1, 0);
    }

    @Test
    void testCreateProject() throws DataAccessException {
        //Arrange
        Project project = new Project();
        project.setEmployee(new Employee(1, "", ""));
        project.setEstimatedHours(63);
        ProjectInvoice invoice = new ProjectInvoice();
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setDueDate(LocalDate.now());
        invoice.setHasPaid(new Price(98000));
        invoice.setToPay(new Price(98000));
        project.setInvoice(invoice);
        project.setStatus(ProjectStatus.ON_HOLD);
        project.setName("bacon pancakes");
        project.setPrice(new Price(98000));
        project.setCustomer(new Customer(3, "", "", "", "",
                new Address("", 4, "", 5)));
        Project returnProject;

        //Act
        returnProject = dao.create(project, false);

        //Assert
        assertNotNull(returnProject);
    }

    @Test
    void testCantUpdateProjectIfItDoesntExistInDatabase() throws DataAccessException {
        //Arrange
        int id = 7000;
        Project project = new Project();
        project.setEmployee(new Employee(1, "", ""));
        project.setEstimatedHours(63);
        ProjectInvoice invoice = new ProjectInvoice();
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setDueDate(LocalDate.now());
        invoice.setHasPaid(new Price(98000));
        invoice.setToPay(new Price(98000));
        project.setInvoice(invoice);
        project.setId(id);
        project.setStatus(ProjectStatus.ON_HOLD);
        project.setName("Jørgens Loftsvinduer");
        project.setPrice(new Price(98000));
        project.setCustomer(new Customer(3, "", "", "", "",
                new Address("", 4, "", 5)));

        assertThrows(DataAccessException.class, () -> dao.update(project, false));

    }

    @Test
    void testCanUpdateProject() {
        //Arrange
        int id = 3;
        Project project = new Project();
        project.setEmployee(new Employee(1, "", ""));
        project.setEstimatedHours(63);
        ProjectInvoice invoice = new ProjectInvoice();
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setDueDate(LocalDate.now());
        invoice.setHasPaid(new Price(98000));
        invoice.setToPay(new Price(98000));
        project.setInvoice(invoice);
        project.setId(id);
        project.setStatus(ProjectStatus.ON_HOLD);
        project.setName("Jørgens Loftsvinduer");
        project.setPrice(new Price(98000));
        project.setCustomer(new Customer(3, "", "", "", "",
                new Address("", 4, "", 5)));

    }

    @AfterAll
    static void teardown() {
        connection.release();
    }
}
