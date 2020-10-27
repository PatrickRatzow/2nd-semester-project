package controller;

import database.IProductDB;
import database.ProductDB;
import model.ArgumentException;
import model.DataAccessException;
import model.DataWriteException;
import model.Product;

import java.sql.SQLException;
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

    public Product findByName(String name) throws DataAccessException {
        return productDB.findByName(name);
    }

    public List<Product> findByCategoryName(String name) throws DataAccessException {
        return productDB.findByCategoryName(name);
    }

    public Product create(Product product) throws ArgumentException, DataWriteException {
        if (product.getCategory() == null) {
            throw new ArgumentException("No category set");
        }

        return productDB.create(product.getName(), product.getDesc(), product.getCategory(), product.getPrice().getAmount());
    }

    public void update(Product product) throws DataWriteException {
        productDB.update(product.getId(), product.getName(), product.getDesc(), product.getCategory(), product.getPrice().getAmount());
    }
}
