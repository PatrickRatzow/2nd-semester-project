package controller;

import database.DBConnection;
import database.DataAccessException;
import database.DataWriteException;
import model.ProductCategory;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductCategoryControllerTest {
    private static ProductCategoryController productCategoryController = new ProductCategoryController();
    private static Savepoint savepoint;
    private static DBConnection conn = DBConnection.getInstance();

    @BeforeAll
    static void setUpAll() throws SQLException {
        conn.startTransaction();
        savepoint = conn.setSavepoint();
    }

    @Test
    @Order(1)
    @DisplayName("findAll() can find all the categories in the database")
    void testFindAllCategories() throws SQLException, DataAccessException {
        // Arrange
        final List<ProductCategory> categories;
        final int expectedSize = 3;

        // Act
        categories = productCategoryController.findAll(false);

        // Assert
        assertEquals(categories.size(), expectedSize);
    }

    @Test
    @Order(2)
    @DisplayName("findAll(populateProducts) can find all the categories in the database")
    void testFindAllCategoriesPopulate() throws SQLException, DataAccessException {
        // Arrange
        final List<ProductCategory> categories;
        final int expectedSize = 3;
        final Map<String, Integer> categoryMap = new HashMap<>();
        categoryMap.put("Tagsten", 3);
        categoryMap.put("Teglsten", 3);
        categoryMap.put("Obamasten", 0);
        // Act
        categories = productCategoryController.findAll(true);

        // Assert
        assertEquals(categories.size(), expectedSize);
        categories.forEach(c -> {
            final int productLength = c.getProducts().size();
            final int mapLength = categoryMap.get(c.getName());

            assertEquals(productLength, mapLength);
        });
    }

    @Test
    @DisplayName("findByName() can find an existing category")
    void testFindByCategoryName_shouldReturnPopulatedListWithAtLeastOneCategory_whenPresentInDatabase() throws DataAccessException, SQLException {
        final String name = "Test Category";
        final List<ProductCategory> categories;

        // Act
        categories = productCategoryController.findByName(name, false);

        // Assert
        assertNotNull(categories.get(0));
    }

    @Test
    @DisplayName("findByName(populateProducts) adds expected amount of products to the category")
    void testFindByCategoryNamePopulated() throws DataAccessException, SQLException {
        final String name = "Tagsten";
        final ProductCategory category;

        // Act
        category = productCategoryController.findByName(name, true).get(0);

        // Assert
        assertNotNull(category);
        assertEquals(category.getProducts().size(), 3);
    }

    @Test
    @DisplayName("findByName() throws a DataAccessException if no such name exists")
    void testFindByCategoryName_shouldReturnEmptyList_whenNotPresentInDatabase()  {
        final String name = "I do not existttttttttttttttttttt";

        // Act
        assertThrows(DataAccessException.class, () -> productCategoryController.findByName(name, false));
    }

    @Test
    @DisplayName("create() works if there's not a category with that name")
    void testCanCreateCategory() throws DataWriteException {
        // Arrange
        final String name = "Test Category";
        final String desc = "Test Description";
        final ProductCategory category = new ProductCategory();
        category.setName(name);
        category.setDesc(desc);
        final ProductCategory returnCategory;

        // Act
        returnCategory = productCategoryController.create(category);

        // Assert
        assertTrue(returnCategory instanceof ProductCategory);
    }

    @Test
    @DisplayName("create() throws DataWriteException if there's an existing category with the same name")
    void testCantCreateCategory() {
        // Arrange
        final String name = "Tagsten";
        final String desc = "Test Description";
        final ProductCategory category = new ProductCategory();
        category.setName(name);
        category.setDesc(desc);

        assertThrows(DataWriteException.class, () -> productCategoryController.create(category));
    }

    @Test
    @DisplayName("update() works")
    void testCanUpdateCategory() throws DataWriteException {
        // Arrange
        final int id = 2;
        final String name = "Renamed Category";
        final String desc = "Description";
        final ProductCategory category = new ProductCategory();
        category.setId(id);
        category.setName(name);
        category.setDesc(desc);

        // Act
        productCategoryController.update(category);

        // Assert
        // Well.. it would throw an exception if it failed to update, so yeh.
    }

    @Test
    @DisplayName("update() throws a DataWriteException if id does not exist in database")
    void testCantUpdateCategory() {
        // Arrange
        final int id = 891289349;
        final String name = "Test Category";
        final String desc = "Test Description";
        final ProductCategory category = new ProductCategory();
        category.setId(id);
        category.setName(name);
        category.setDesc(desc);

        // Act
        assertThrows(DataWriteException.class, () -> productCategoryController.update(category));
    }

    @Test
    @DisplayName("update() throws a IllegalArgumentException if no id has been set on the ProductCategory")
    void testCantUpdateCategoryIfIdNotSet() {
        // Arrange
        final String name = "Renamed Category";
        final String desc = "Description";
        final ProductCategory category = new ProductCategory();
        category.setName(name);
        category.setDesc(desc);

        // Act + assert
        assertThrows(IllegalArgumentException.class, () -> productCategoryController.update(category));
    }

    @Test
    @DisplayName("delete() can delete an existing category")
    void testCanDeleteCategory() throws DataWriteException, IllegalArgumentException {
        // Arrange
        final int id = 3;
        final ProductCategory category = new ProductCategory();
        category.setId(id);

        // Act
        productCategoryController.delete(category);

        // Assert
        // It would have thrown a DataWriteException if it failed.. so
    }

    @Test
    @DisplayName("delete() throws a DataWriteException if it was unable to delete a category")
    void testCantDeleteCategory() {
        // Arrange
        final int id = 123467822;
        final ProductCategory category = new ProductCategory();
        category.setId(id);

        // Act
        assertThrows(DataWriteException.class, () -> productCategoryController.delete(category));
    }

    @Test
    @DisplayName("delete() throws a IllegalArgumentException if no id has been set on the category")
    void testCantDeleteCategoryIfIdNotSet() {
        // Arrange
        final ProductCategory category = new ProductCategory();

        // Act
        assertThrows(IllegalArgumentException.class, () -> productCategoryController.delete(category));
    }

    @Test
    @DisplayName("findById() returns ProductCategory if id in database")
    void testCanFindById() throws DataAccessException, SQLException {
        // Arrange
        final int id = 1;
        final ProductCategory category;

        // Act
        category = productCategoryController.findById(id, false);

        // Assert
        assertNotNull(category);
    }

    @Test
    @DisplayName("findById(populateProducts) returns ProductCategory with products if id in database")
    void testCanFindByIdPopulate() throws DataAccessException, SQLException {
        // Arrange
        final int id = 1;
        final ProductCategory category;
        final int expectedProductsSize = 3;

        // Act
        category = productCategoryController.findById(id, true);

        // Assert
        assertEquals(category.getProducts().size(), expectedProductsSize);
    }

    @Test
    @DisplayName("findById() throws DataAccessException if id does not exist in database")
    void testCantFindByIdIfIdDoesNotExist() {
        // Arrange
        final int id = 818923812;

        // Act + assert
        assertThrows(DataAccessException.class, () -> productCategoryController.findById(id, false));
    }

    @AfterAll
    static void tearDownAll() throws SQLException {
        conn.getConnection().setAutoCommit(false);
        conn.rollbackTransaction(savepoint);
    }
}