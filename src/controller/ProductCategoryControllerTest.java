package controller;

import model.DataExistsException;
import model.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryControllerTest {
    private static ProductCategoryController productCategoryController = new ProductCategoryController();

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Can find existing category by name")
    void testFindByCategoryName_shouldReturnPopulatedListWithAtLeastOneCategory_whenPresentInDatabase() {
        String name = "Test Category";
        List<ProductCategory> returnCategories;
        List<ProductCategory> categories = new ArrayList<>();
        ProductCategory category = new ProductCategory();
        category.setName(name);
        categories.add(category);

        // Act
        returnCategories = productCategoryController.findByName(name);

        // Assert
        assertNotNull(returnCategories.get(0));
    }

    @Test
    @DisplayName("Can't find category by name that doesn't exist")
    void testFindByCategoryName_shouldReturnEmptyList_whenNotPresentInDatabase() {
        String name = "I do not existssssssss";
        List<ProductCategory> returnCategories;
        List<ProductCategory> categories = new ArrayList<>();

        // Act
        returnCategories = productCategoryController.findByName(name);

        // Assert
        assertTrue(returnCategories.size() == 0);
    }

    @Test
    @DisplayName("Can create category if the name doesn't exist")
    void testCanCreateCategory() throws DataExistsException {
        // Arrange
        String name = "Test Category";
        String desc = "Test Description";
        ProductCategory returnCategory;
        ProductCategory category = new ProductCategory();
        category.setName(name);
        category.setDesc(desc);

        // Act
        returnCategory = productCategoryController.createCategory(name, desc);

        // Assert
        assertNotNull(returnCategory);
    }

    @Test
    @DisplayName("Can't create category if there's a category with the same name")
    void testCantCreateCategory() throws DataExistsException {
        // Arrange
        String name = "Existing Category";
        String desc = "Test Description";
        ProductCategory category = new ProductCategory();
        category.setName(name);
        category.setDesc(desc);

        assertThrows(DataExistsException.class, () -> {
            productCategoryController.createCategory(name, desc);
        });
    }

    @Test
    @DisplayName("Can update category")
    void testCanUpdateCategory() {
        fail();
    }

    @Test
    @DisplayName("Can delete category")
    void testCanDeleteCategory() {
        fail();
    }
}