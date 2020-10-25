package controller;

import model.ProductCategory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductCategoryControllerTest {
    private static ProductCategoryController productCategoryController;

    @BeforeEach
    void setUp() {
        productCategoryController = spy(new ProductCategoryController());
    }

    @Test
    @DisplayName("Can find category by name")
    void testCanFindCategoryByName() {
        fail();
    }

    @Test
    @DisplayName("Can create category")
    void testCanCreateCategory() {
        // Arrange
        String name = "Test Category";
        String desc = "Test Description";
        ProductCategory returnCategory;
        ProductCategory category = new ProductCategory();
        category.setName(name);
        category.setDesc(desc);
        when(productCategoryController.createCategory(name, desc)).thenReturn(category);

        // Act
        returnCategory = productCategoryController.createCategory(name, desc);

        // Assert
        assertNotNull(returnCategory);
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