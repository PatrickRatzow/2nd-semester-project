package controller;


import database.IProductCategoryDB;
import database.ProductCategoryDB;
import model.DataExistsException;
import model.ProductCategory;

import java.util.List;

public class ProductCategoryController {
    IProductCategoryDB productCategoryDB = new ProductCategoryDB();

    public ProductCategory createCategory(String name, String desc) throws DataExistsException {
        return productCategoryDB.create(name, desc);
    }

    public List<ProductCategory> findByName(String name) {
        return productCategoryDB.findByName(name);
    }
}
