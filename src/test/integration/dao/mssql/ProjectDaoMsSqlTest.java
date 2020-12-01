package test.integration.dao.mssql;

import dao.ProjectDao;
import dao.mssql.ProjectDaoMsSql;
import datasource.DBConnection;
import datasource.DBManager;
import entity.Project;
import exception.DataAccessException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProjectDaoMsSqlTest {
    private static DBConnection connection;
    private static ProjectDao dao;

    @BeforeAll
    static void setup() {
        connection = DBManager.getPool().getConnection();
        dao = new ProjectDaoMsSql(connection);
    }

    @Test
    void testCanFindById() throws DataAccessException {
        //Arrange
        Project project;

        //act
        project = dao.findById(3,true);

        //assert
        assertNotNull(project);

    }

    @Test
    void testCanFindByName() throws DataAccessException {
        //Arrange
        String name = "The Project";
        List<Project> projects;

        //act
        projects = dao.findByName(name, false);

        //assert
        assertEquals(projects.size(),1);
    }

}
