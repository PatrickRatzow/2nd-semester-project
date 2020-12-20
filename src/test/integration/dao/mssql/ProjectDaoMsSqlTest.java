package test.integration.dao.mssql;

import dao.ProjectDao;
import datasource.DBConnection;
import datasource.DBManager;
import datasource.DataAccessException;
import model.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProjectDaoMsSqlTest {
    private static DBConnection connection;
    private static ProjectDao dao;

    @BeforeAll
    static void setup() {
        connection = DBManager.getInstance().getPool().getConnection();
        dao = connection.getDaoFactory().createProjectDao();
    }

    @Order(1)
    @Test
    void testCanFindAllExpectedProjects() throws DataAccessException {
        // Arrange
        List<Project> projects;

        // Act
        projects = dao.findAll(false);

        // Assert
        assertEquals(projects.size(), 4);
    }
    
    @Order(2)
    @Test
    void testCanFindById() throws DataAccessException {
        //Arrange
        Project project;

        //act
        project = dao.findById(3, true);

        //assert
        assertNotNull(project);
    }
    
    @Order(3)
    @Test
    void testCantFindByIdIfItDoesntExistInDatabase() throws DataAccessException {
        //Arrange
        Project project;

        //act
        project = dao.findById(99999999, false);

        //assert
        assertNull(project);
    }
    
    @Order(4)
    @Test
    void testCanFindByName() throws DataAccessException {
        //Arrange
        String name = "Skur #1";
        List<Project> projects;

        //act
        projects = dao.findByName(name, false);

        //assert
        assertEquals(projects.size(), 1);
    }
    
    @Order(5)
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
        project.setStatus(ProjectStatus.ON_HOLD);
        project.setName("bacon pancakes");
        project.setPrice(new Price(98000));
        project.setCustomer(new Customer(1, "", "", "", "",
                new Address("", 4, "", 5)));
        Project returnProject;

        //Act
        returnProject = dao.create(project);

        //Assert
        assertNotNull(returnProject);
    }

    @Test
    void testCantUpdateProjectIfItDoesntExistInDatabase() {
        //Arrange
        int id = 7000;
        Project project = new Project();
        project.setEmployee(new Employee(1, "", ""));
        project.setEstimatedHours(63);
        project.setId(id);
        project.setStatus(ProjectStatus.ON_HOLD);
        project.setName("Jørgens Loftsvinduer");
        project.setPrice(new Price(98000));
        project.setCustomer(new Customer(3, "", "", "", "",
                new Address("", 4, "", 5)));

        assertThrows(DataAccessException.class, () -> dao.update(project));
    }

    @Test
    void testCanUpdateProject() {
        // Arrange
        int id = 3;
        Project project = new Project();
        project.setEmployee(new Employee(1, "", ""));
        project.setEstimatedHours(63);
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
