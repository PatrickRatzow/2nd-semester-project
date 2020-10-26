package database;

import model.DataExistsException;
import model.ProductCategory;

import java.util.Collection;
import java.util.List;

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
    public List<ProductCategory> findByName(String name) {
        return null;
    }

    @Override
    public ProductCategory create(String name, String desc) throws DataExistsException {
        return null;
    }

    @Override
    public void update(ProductCategory category) {

    }

    @Override
    public void delete(ProductCategory category) {

    }
}
