package database;

import model.DataExistsException;
import model.ProductCategory;

import java.util.Collection;
import java.util.List;

public interface IProductCategoryDB {
    Collection<ProductCategory> findAll();

    ProductCategory findById(int id);

    List<ProductCategory> findByName(String name);

    ProductCategory create(String name, String desc) throws DataExistsException;

    void update(ProductCategory category);

    void delete(ProductCategory category);
}
