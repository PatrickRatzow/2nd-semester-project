package controller;

import database.IProductDB;
import database.ProductDB;
import model.Product;

import java.util.Collection;

/**
 * The type Product controller.
 */
public class ProductController {
    /**
     * The Product db.
     */
    IProductDB productDB = new ProductDB();

    public Collection<Product> findAll() {
        return productDB.findAll();
    }
}
