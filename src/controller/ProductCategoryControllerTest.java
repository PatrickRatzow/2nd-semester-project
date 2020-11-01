package controller;

import database.DBConnection;
import database.DataAccessException;
import database.DataWriteException;
import model.ProductCategory;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryControllerTest {
    private static ProductCategoryController productCategoryController = new ProductCategoryController();

    @BeforeAll
    static void setUpAll() throws SQLException {
        DBConnection.getInstance().startTransaction();
    }

    @Test
    @DisplayName("findAll() can find all the categories in the database")
    void testFindAllCategories() {
        // Arrange
        final List<ProductCategory> categories;
        final int expectedSize = 3;

        // Act
        categories = productCategoryController.findAll();

        // Assert
        assertEquals(categories.size(), expectedSize);
        categories.forEach(Assertions::assertNotNull);
    }

    @Test
    @DisplayName("findByName() can find an existing category")
    void testFindByCategoryName_shouldReturnPopulatedListWithAtLeastOneCategory_whenPresentInDatabase() throws DataAccessException {
        final String name = "Test Category";
        final List<ProductCategory> returnCategories;

        // Act
        returnCategories = productCategoryController.findByName(name);

        // Assert
        assertNotNull(returnCategories.get(0));
    }

    @Test
    @DisplayName("findByName() throws a DataAccessException if no such name exists")
    void testFindByCategoryName_shouldReturnEmptyList_whenNotPresentInDatabase()  {
        final String name = "I do not existssssssss";

        // Act
        assertThrows(DataAccessException.class, () -> productCategoryController.findByName(name));
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
        final String name = "Mursten";
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
    void testCanFindById() throws DataAccessException {
        // Arrange
        final int id = 1;
        final ProductCategory category;

        // Act
        category = productCategoryController.findById(id);

        // Assert
        assertNotNull(category);
    }

    @Test
    @DisplayName("findById() throws DataAccessException if id does not exist in database")
    void testCantFindByIdIfIdDoesNotExist() {
        // Arrange
        final int id = 818923812;

        // Act + assert
        assertThrows(DataAccessException.class, () -> productCategoryController.findById(id));
    }

    @AfterAll
    static void tearDownAll() throws SQLException {
        DBConnection.getInstance().rollbackTransaction();
    }
}