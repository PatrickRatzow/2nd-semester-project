package database;

import model.Product;

import java.util.Collection;

public interface IProductDB {
    Collection<Product> findAll();
    Product findById(int id);
    Collection<Product> findByName(String name);
    Collection<Product> findByCategoryName(String name);
    Product create(Product product);
    void update(Product product);
    void delete(Product product);
}
