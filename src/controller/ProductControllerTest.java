package controller;

import database.DBConnection;
import database.IProductDB;
import database.ProductDB;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.fail;

class ProductControllerTest {
    private static IProductDB productDB = new ProductDB();

    @BeforeAll
    static void setUp() throws SQLException {
        DBConnection.getInstance().startTransaction();

        productDB.create("Hey there", "MONKA", "Tagsten", 100000);
        productDB.create("MonkaS", "MONKA", "Tagsten", 1500000);
    }

    @Test
    @DisplayName("Can find all")
    void testFindAll() {
        fail();
    }

    @Test
    void testFindById() {
        fail();
    }

    @Test
    void testFindByName() {
        fail();
    }

    @Test
    void testFindByCategoryName() {
        fail();
    }

    @Test
    void testCreate() {
        fail();
    }

    @Test
    void testUpdate() {
        fail();
    }

    @Test
    void testDelete() {
        fail();
    }

    @AfterAll
    static void tearDown() throws SQLException {
        DBConnection.getInstance().rollbackTransaction();
    }
}