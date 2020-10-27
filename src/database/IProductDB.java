package database;

import model.Product;

import java.util.List;

public interface IProductDB {
    List<Product> findAll();

    Product findById(int id);

    List<Product> findByName(String name);

    List<Product> findByCategoryName(String name);

    Product create(Product product);

    void update(Product product);

    void delete(Product product);
}
