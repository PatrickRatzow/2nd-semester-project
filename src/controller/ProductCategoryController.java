package controller;

import database.DataAccessException;
import database.DataWriteException;
import database.IProductCategoryDB;
import database.ProductCategoryDB;
import model.*;

import java.util.List;

public class ProductCategoryController {
    IProductCategoryDB productCategoryDB = new ProductCategoryDB();

    public List<ProductCategory> findAll() {
        return productCategoryDB.findAll();
    }

    public List<ProductCategory> findByName(String name) throws DataAccessException {
        return productCategoryDB.findByName(name);
    }

    public ProductCategory findById(int id) throws DataAccessException {
        return productCategoryDB.findById(id);
    }

    public ProductCategory create(ProductCategory category) throws DataWriteException {
        return productCategoryDB.create(category.getName(), category.getDesc());
    }

    public void update(ProductCategory category) throws DataWriteException, IllegalArgumentException {
        if (category.getId() == 0) {
            throw new IllegalArgumentException("Category does not have any id set on it");
        }

        productCategoryDB.update(category.getId(), category.getName(), category.getDesc());
    }

    public void delete(ProductCategory category) throws DataWriteException, IllegalArgumentException {
        if (category.getId() == 0) {
            throw new IllegalArgumentException("Category does not have any id set on it");
        }

        productCategoryDB.delete(category.getId());
    }
}
