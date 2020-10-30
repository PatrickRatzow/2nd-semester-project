package controller;

import database.IProductDB;
import database.ProductDB;
import model.*;

import java.sql.SQLException;
import java.util.List;

public class ProductController {
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

    public Product create(Product product, ProductCategory category, Supplier supplier) throws ArgumentException, DataWriteException {
        // FIXME: Yeh we need actual verification or something
        if (category == null) {
            throw new ArgumentException("No category set");
        }
        if (supplier == null) {
            throw new ArgumentException("No supplier set");
        }

        return productDB.create(product.getName(), product.getDesc(), category.getId(), supplier.getId(), product.getPrice().getAmount());
    }

    public void update(Product product, ProductCategory category, Supplier supplier) throws DataWriteException, ArgumentException {
        // FIXME: Yeh we need actual verification or something
        if (category == null) {
            throw new ArgumentException("No category set");
        }
        if (supplier == null) {
            throw new ArgumentException("No supplier set");
        }

        productDB.update(product.getId(), product.getName(), product.getDesc(), category.getId(), supplier.getId(), product.getPrice().getAmount());
    }
}
