package database;

import model.DataAccessException;
import model.Product;

import java.util.List;

public interface IProductDB {
    List<Product> findAll();
    Product findById(int id) throws DataAccessException;
    List<Product> findByName(String name);
    List<Product> findByCategoryName(String name);
    Product create(String name, String description, String categoryName, int price);
    void update(int id, String name, String description, String categoryName, int price);
}
