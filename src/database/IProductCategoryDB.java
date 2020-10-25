package database;

import model.Product;
import model.ProductCategory;

import java.util.Collection;

public interface IProductCategoryDB {
    Collection<ProductCategory> findAll();
    ProductCategory findById(int id);
    Collection<ProductCategory> findByName(String name);
    ProductCategory create(String name, String desc);
    void update(ProductCategory category);
    void delete(ProductCategory category);
}
