package test.service.mssql;

public class ProductCategoryServiceMsSqlTest {
    /*
    private final ProductCategoryService productCategoryService = new ProductCategoryServiceMsSql();

    @BeforeAll
    static void setUpAll() throws SQLException {
        DataSourceMsSql.getInstance().startTransaction();
    }

    @Test
    @DisplayName("findById(id) finds an order if it exists in database")
    void canFindProductCategoryByExistingId() throws DataAccessException {
        // Arrange
        final ProductCategory productCategory;
        final int id = 1;

        // Act
        productCategory = productCategoryService.findById(id);

        // Assert
        assertNotNull(productCategory);
    }

    @Test
    @DisplayName("findById(id) throws a DataAccessException if it doesn't exist")
    void cantFindProductCategoryIfIdIsntInDatabase(){
        // Arrange
        final int id = 50;

        // Act
        assertThrows(DataAccessException.class, () -> productCategoryService.findById(id));
    }

    @AfterAll
    static void tearDownAll() throws SQLException {
        DataSourceMsSql.getInstance().commitTransaction();
    }

     */
}
