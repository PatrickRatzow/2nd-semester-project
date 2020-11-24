package test.controller;

import controller.ProductController;
import datasource.mssql.DataSourceMsSql;
import exception.DataAccessException;
import exception.DataWriteException;
import model.Price;
import model.Product;
import model.ProductCategory;
import model.Supplier;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {
    private final ProductCategory categoryMursten = new ProductCategory(1, "Mursten", "");
    private final Supplier supplierBygma = new Supplier(1, "Bygma");
    private ProductController productController;

    @BeforeAll
    static void setUpAll() throws SQLException {
        DataSourceMsSql.getInstance().startTransaction();
    }

    @BeforeEach
    void setUp() {
        productController = new ProductController();
    }

    @Test
    @Order(1)
    @DisplayName("findByCategoryName() finds all the expected Products if match")
    void testCanFindProductsByCategoryNameIfCategoryNameExistsInDatabase() throws DataAccessException {
        // Arrange
        final List<Product> products;
        final Set<String> productNames = new HashSet<>();
        productNames.add("Lille tagsten");
        productNames.add("Tagsten");
        productNames.add("Stor tagsten");
        final String name = "Tagsten";

        // Act
        products = productController.findByCategoryName(name);

        // Assert
        // Make sure we get the 3 products we have in our seeding
        assertEquals(products.size(), 3);
        // Each product has to be in our productNames set
        products.forEach(p -> assertTrue(productNames.contains(p.getName())));
    }

    @Test
    @DisplayName("findByAll() returns a non empty List with only Product instances")
    void testCanFindAllProductsInDatabase() {
        // Arrange
        final List<Product> products;

        // Act
        products = productController.findAll();

        // Assert that our list is bigger than 0
        assertTrue(products.size() > 0);
        // Assert that every product is actually a Product, and not anything else
        products.forEach(p -> assertTrue(p instanceof Product));
    }

    @Test
    @DisplayName("findById() returns a Product if there's a match")
    void testCanFindProductByIdThatExistsInDatabase() throws DataAccessException {
        // Arrange
        final Product product;
        final int id = 1;

        // Act
        product = productController.findById(id);

        // Assert
        assertTrue(product instanceof Product);
    }

    @Test
    @DisplayName("findById() throws DataAccessException if no match")
    void testCantFindProductByIdThatExistsInDatabase() {
        final int id = 9999999;

        assertThrows(DataAccessException.class, () -> productController.findById(id));
    }


    @Test
    @DisplayName("findByName() returns a Product if there's a match")
    void testCanFindProductByNameThatExistsInDatabase() throws DataAccessException {
        // Arrange
        final Product product;
        final String name = "Lille tagsten";

        // Act
        product = productController.findByName(name).get(0);

        // Assert
        assertTrue(product instanceof Product);
    }

    @Test
    @DisplayName("findByName() throws DataAccessException if no match")
    void testCantFindProductByNameThatDoesntExistInDatabase() {
        // Arrange
        final String name = "Ho123hi0s18231z11z23s1a23y890s";

        // Assert + Act
        assertThrows(DataAccessException.class, () -> productController.findByName(name));
    }

    @Test
    @DisplayName("findByCategoryName() throws DataAccessException if no match")
    void testCantFindProductsByCategoryNameIfCategoryNameDoesntExistInDatabase() {
        // Arrange
        final String name = "Some random text that could never possibly exist...z.z.1z23z12zajisd";

        // Act
        assertThrows(DataAccessException.class, () -> productController.findByCategoryName(name));
    }

    @Test
    @DisplayName("create() returns a Product object with id set with the product's identity")
    void testCanCreate() throws IllegalArgumentException, DataWriteException {
        // Arrange
        final String name = "Test Create";
        final String desc = "Test Desc";
        final ProductCategory category = categoryMursten;
        final Supplier supplier = supplierBygma;
        final Price price = new Price(100 * 100);
        final Product product = new Product(name, desc, price);
        final Product returnProduct;

        // Act
        returnProduct = productController.create(product, category, supplier);

        // Assert
        assertTrue(returnProduct.getId() == 7);
    }

    @Test
    @DisplayName("create() throws IllegalArgumentException if category is null")
    void testCantCreateWithoutCategory() {
        // Arrange
        final String name = "Test Create";
        final String desc = "Test Desc";
        final Supplier supplier = supplierBygma;
        final Price price = new Price(100 * 100);
        final Product product = new Product(name, desc, price);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> productController.create(product, null, supplier));
    }

    @Test
    @DisplayName("create() throws IllegalArgumentException if supplier is null")
    void testCantCreateWithoutSupplier() {
        // Arrange
        final String name = "Test Create";
        final String desc = "Test Desc";
        final ProductCategory category = categoryMursten;
        final Price price = new Price(100 * 100);
        final Product product = new Product(name, desc, price);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> productController.create(product, category, null));
    }

    @Test
    @DisplayName("update() can update existing Product in database")
    void testCanUpdate() throws DataAccessException, IllegalArgumentException, DataWriteException {
        // Arrange
        final String name = "Renamed Product";
        final String desc = "Renamed Desc";
        final ProductCategory category = categoryMursten;
        final Supplier supplier = supplierBygma;
        final Price price = new Price(150 * 100);
        final int id = 1;
        final Product product = new Product(name, desc, price);
        product.setId(id);
        final Product returnProduct;

        // Act
        productController.update(product, category, supplier);
        returnProduct = productController.findById(id);

        // Assert
        assertEquals(returnProduct.getName(), name);
        assertEquals(returnProduct.getDesc(), desc);
        assertEquals(returnProduct.getPrice().getAmount(), price.getAmount());
    }

    @Test
    @DisplayName("update() throws DataWriteException if id doesn't exist in database")
    void testCantUpdate() {
        // Arrange
        final String name = "Renamed Product";
        final String desc = "Renamed Desc";
        final ProductCategory category = categoryMursten;
        final Supplier supplier = supplierBygma;
        final Price price = new Price(150 * 100);
        final int id = 9999995;
        final Product product = new Product(name, desc, price);
        product.setId(id);

        // Act
        assertThrows(DataWriteException.class, () -> productController.update(product, category, supplier));
    }

    @Test
    @DisplayName("update() throws IllegalArgumentException if category is null")
    void testCantUpdateWithoutCategory() {
        // Arrange
        final String name = "Renamed Product";
        final String desc = "Renamed Desc";
        final Supplier supplier = supplierBygma;
        final Price price = new Price(100 * 100);
        final int id = 1;
        final Product product = new Product(name, desc, price);
        product.setId(id);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> productController.update(product, null, supplier));
    }

    @Test
    @DisplayName("update() throws IllegalArgumentException if supplier is null")
    void testCantUpdateWithoutSupplier() {
        // Arrange
        final String name = "Test Create";
        final String desc = "Test Desc";
        final ProductCategory category = categoryMursten;
        final Price price = new Price(100 * 100);
        final int id = 928103984;
        final Product product = new Product(name, desc, price);
        product.setId(id);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> productController.update(product, category, null));
    }

    @Test
    @DisplayName("update() throws IllegalArgumentException if ID has never been set")
    void testCantUpdateWithInvalidId() {
        // Arrange
        final String name = "Test Create";
        final String desc = "Test Desc";
        final Supplier supplier = supplierBygma;
        final ProductCategory category = categoryMursten;
        final Price price = new Price(100 * 100);
        final Product product = new Product(name, desc, price);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> productController.update(product, category, supplier));
    }

    @AfterAll
    static void tearDownAll() throws SQLException {
        DataSourceMsSql.getInstance().rollbackTransaction();
    }
}