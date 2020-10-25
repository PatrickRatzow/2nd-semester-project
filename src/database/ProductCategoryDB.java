package database;

import model.ProductCategory;

import java.util.Collection;

public class ProductCategoryDB implements IProductCategoryDB {
    @Override
    public Collection<ProductCategory> findAll() {
        return null;
    }

    @Override
    public ProductCategory findById(int id) {
        return null;
    }

    @Override
    public Collection<ProductCategory> findByName(String name) {
        return null;
    }

    @Override
    public ProductCategory create(String name, String desc) {
        return null;
    }

    @Override
    public void update(ProductCategory category) {

    }

    @Override
    public void delete(ProductCategory category) {

    }
}
