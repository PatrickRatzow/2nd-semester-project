package test.service.mssql;

public class OrderServiceMsSqlTest {
    /*
    private final OrderService orderService = new OrderServiceMsSql();

    @BeforeAll
    static void setUpAll() throws SQLException {
        DataSourceMsSql.getInstance().startTransaction();
    }

    @Test
    @DisplayName("findById(id) finds an order if it exists in database")
    void canFindOrderByExistingId() throws DataAccessException {
        // Arrange
        final Order order;
        final int id = 1;

        // Act
        order = orderService.findById(id);

        // Assert
        assertNotNull(order);
    }

    @Test
    @DisplayName("findById(id) throws a DataAccessException if it doesn't exist")
    void cantFindOrderIfIdIsntInDatabase(){
        // Arrange
        final int id = 450;

        // Act
        assertThrows(DataAccessException.class, () -> orderService.findById(id));
    }

    @AfterAll
    static void tearDownAll() throws SQLException {
        DataSourceMsSql.getInstance().commitTransaction();
    }
     */
}
