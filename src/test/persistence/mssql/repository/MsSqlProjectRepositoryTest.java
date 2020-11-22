package test.persistence.mssql.repository;

import exception.DataAccessException;
import model.Project;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.connection.mssql.MsSqlDataSource;
import persistence.repository.ProjectRepository;
import persistence.repository.mssql.MsSqlProjectRepository;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MsSqlProjectRepositoryTest {
    private final ProjectRepository projectRepository = new MsSqlProjectRepository();

    @BeforeAll
    static void setUpAll() throws SQLException {
        MsSqlDataSource.getInstance().startTransaction();
    }

    @Test
    @DisplayName("findById(id) finds a project if it exists in database")
    void canFindProjectByExistingId() throws DataAccessException {
        // Arrange
        final Project project;
        final int id = 1;

        // Act
        project = projectRepository.findById(id);

        // Assert
        assertNotNull(project);
    }

    @Test
    @DisplayName("findById(id) throws a DataAccessException if it doesn't exist")
    void cantFindProjectIfIdIsntInDatabase(){
        // Arrange
        final int id = 900;

        // Act
        assertThrows(DataAccessException.class, () -> projectRepository.findById(id));
    }

    @AfterAll
    static void tearDownAll() throws SQLException {
        MsSqlDataSource.getInstance().commitTransaction();
    }
}
