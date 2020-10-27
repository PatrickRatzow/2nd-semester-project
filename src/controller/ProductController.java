package controller;

import database.IProductDB;
import database.ProductDB;
import model.DataAccessException;
import model.Product;

import java.util.List;

/**
 * The type Product controller.
 */
public class ProductController {
    /**
     * The Product db.
     */
    IProductDB productDB = new ProductDB();

    public List<Product> findAll() {
        return productDB.findAll();
    }

    public Product findById(int id) throws DataAccessException {
        return productDB.findById(id);
    }

    public List<Product> findByName(String name) {
        return productDB.findByName(name);
    }

    public List<Product> findByCategoryName(String name) {
        return productDB.findByCategoryName(name);
    }

    public Product create(Product product) {
        return productDB.create(product.getName(), product.getDesc(), product.getCategory(), product.getPrice().getAmount());
    }

    public void update(Product product) {
        productDB.update(product.getId(), product.getName(), product.getDesc(), product.getCategory(), product.getPrice().getAmount());
    }
}
