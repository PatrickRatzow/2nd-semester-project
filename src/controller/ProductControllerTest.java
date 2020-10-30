package controller;

import database.DBConnection;
import database.IProductDB;
import database.ProductDB;
import model.*;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {
    private static IProductDB productDB = new ProductDB();
    private ProductController productController;
    private ProductCategory categoryMursten = new ProductCategory(1, "Mursten", "");
    private ProductCategory categoryTagsten = new ProductCategory(2, "Tagsten", "");
    private Supplier supplierBygma = new Supplier(1, "Bygma");
    private Supplier supplierXL = new Supplier(2, "XL Byg");

    @BeforeAll
    static void setUpAll() throws SQLException {
        DBConnection.getInstance().startTransaction();
    }

    @BeforeEach
    void setUp() {
        productController = new ProductController();
    }

    @Test
    @DisplayName("findByAll() returns a non empty List with only Product instances")
    void testCanFindAllProductsInDatabase() {
        // Arrange
        List<Product> products;

        // Act
        products = productController.findAll();

        // Assert
        // Assert that our list is bigger than 0
        assertTrue(products.size() > 0);
        // Assert that every product is actually a Product, and not anything else
        products.forEach(p -> assertTrue(p instanceof Product));
    }

    @Test
    @DisplayName("findById() returns a Product if there's a match")
    void testCanFindProductByIdThatExistsInDatabase() throws DataAccessException {
        // Arrange
        Product product;
        int id = 1;

        // Act
        product = productController.findById(id);

        // Assert
        assertTrue(product instanceof Product);
    }

    @Test
    @DisplayName("findById() throws DataAccessException if no match")
    void testCantFindProductByIdThatExistsInDatabase() {
        int id = 9999999;

        assertThrows(DataAccessException.class, () -> productController.findById(id));
    }


    @Test
    @DisplayName("findByName() returns a Product if there's a match")
    void testCanFindProductByNameThatExistsInDatabase() throws DataAccessException {
        // Arrange
        Product product;
        String name = "Lille tagsten";

        // Act
        product = productController.findByName(name);

        // Assert
        assertTrue(product instanceof Product);
    }

    @Test
    @DisplayName("findByName() throws DataAccessException if no match")
    void testCantFindProductByNameThatDoesntExistInDatabase() {
        // Arrange
        String name = "Ho123hi0s18231z11z23s1a23y890s";

        // Assert + Act
        assertThrows(DataAccessException.class, () -> productController.findByName(name));
    }

    @Test
    @DisplayName("findByCategoryName() finds all the expected Products if match")
    void testCanFindProductsByCategoryNameIfCategoryNameExistsInDatabase() throws DataAccessException {
        // Arrange
        List<Product> products;
        Set<String> productNames = new HashSet<>();
        productNames.add("Lille tagsten");
        productNames.add("Tagsten");
        productNames.add("Stor tagsten");
        String name = "Tagsten";

        // Act
        products = productController.findByCategoryName(name);

        // Assert
        // Make sure we get the 3 products we have in our seeding
        assertTrue(products.size() == 3);
        // Each product has to be in our productNames set
        products.forEach(p -> assertTrue(productNames.contains(p.getName())));
    }

    @Test
    @DisplayName("findByCategoryName() throws DataAccessException if no match")
    void testCantFindProductsByCategoryNameIfCategoryNameDoesntExistInDatabase() {
        // Arrange
        Set<String> productNames = new HashSet<>();
        String name = "Some random text that could never possibly exist...z.z.1z23z12zajisd";

        // Act
        assertThrows(DataAccessException.class, () -> productController.findByCategoryName(name));
    }

    @Test
    @DisplayName("create() returns a Product object with id set with the product's identity")
    void testCanCreate() throws ArgumentException, DataWriteException {
        // Arrange
        String name = "Test Create";
        String desc = "Test Desc";
        ProductCategory category = categoryMursten;
        Supplier supplier = supplierBygma;
        Price price = new Price(100 * 100);
        Product product = new Product(name, desc, price);
        Product returnProduct;

        // Act
        returnProduct = productController.create(product, category, supplier);

        // Assert
        assertTrue(returnProduct.getId() == 7);
    }

    @Test
    @DisplayName("create() throws ArgumentException if category is null")
    void testCantCreateWithoutCategory() {
        // Arrange
        String name = "Test Create";
        String desc = "Test Desc";
        Supplier supplier = supplierBygma;
        Price price = new Price(100 * 100);
        Product product = new Product(name, desc, price);

        // Act + Assert
        assertThrows(ArgumentException.class, () -> productController.create(product, null, supplier));
    }

    @Test
    @DisplayName("create() throws ArgumentException if supplier is null")
    void testCantCreateWithoutSupplier() {
        // Arrange
        String name = "Test Create";
        String desc = "Test Desc";
        ProductCategory category = categoryMursten;
        Price price = new Price(100 * 100);
        Product product = new Product(name, desc, price);

        // Act + Assert
        assertThrows(ArgumentException.class, () -> productController.create(product, category, null));
    }

    @Test
    @DisplayName("update() can update existing Product in database")
    void testCanUpdate() throws DataAccessException, DataWriteException, ArgumentException {
        // Arrange
        String name = "Renamed Product";
        String desc = "Renamed Desc";
        ProductCategory category = categoryMursten;
        Supplier supplier = supplierBygma;
        Price price = new Price(150 * 100);
        int id = 1;
        Product product = new Product(name, desc, price);
        product.setId(id);
        Product returnProduct;

        // Act
        productController.update(product, category, supplier);
        returnProduct = productController.findById(id);

        // Assert
        assertEquals(returnProduct.getName(), name);
        assertEquals(returnProduct.getDesc(), desc);
        assertEquals(returnProduct.getPrice().getAmount(), price.getAmount());
    }

    @Test
    @DisplayName("update() throws DataAccessException if id doesn't exist in database")
    void testCantUpdate() {
        // Arrange
        String name = "Renamed Product";
        String desc = "Renamed Desc";
        ProductCategory category = categoryMursten;
        Supplier supplier = supplierBygma;
        Price price = new Price(150 * 100);
        int id = 9999995;
        Product product = new Product(name, desc, price);
        product.setId(id);

        // Act
        assertThrows(DataWriteException.class, () -> productController.update(product, category, supplier));
    }

    @AfterAll
    static void tearDownAll() throws SQLException {
        DBConnection.getInstance().rollbackTransaction();
    }
}