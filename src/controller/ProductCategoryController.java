package controller;


import database.IProductCategoryDB;
import database.ProductCategoryDB;
import model.ProductCategory;

public class ProductCategoryController {
    IProductCategoryDB productCategoryDB = new ProductCategoryDB();

    public ProductCategory createCategory(String name, String desc) {
        return productCategoryDB.create(name, desc);
    }
}
