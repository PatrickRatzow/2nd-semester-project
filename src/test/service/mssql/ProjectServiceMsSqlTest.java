package test.service.mssql;

public class ProjectServiceMsSqlTest {
    /*
    private final ProjectService projectService = new ProjectServiceMsSql();

    @BeforeAll
    static void setUpAll() throws SQLException {
        DataSourceMsSql.getInstance().startTransaction();
    }

    @Test
    @DisplayName("findById(id) finds a project if it exists in database")
    void canFindProjectByExistingId() throws DataAccessException {
        // Arrange
        final Project project;
        final int id = 1;

        // Act
        project = projectService.findById(id);

        // Assert
        assertNotNull(project);
    }

    @Test
    @DisplayName("findById(id) throws a DataAccessException if it doesn't exist")
    void cantFindProjectIfIdIsntInDatabase(){
        // Arrange
        final int id = 900;

        // Act
        assertThrows(DataAccessException.class, () -> projectService.findById(id));
    }

    @AfterAll
    static void tearDownAll() throws SQLException {
        DataSourceMsSql.getInstance().commitTransaction();
    }

     */
}
