package dao;

import exception.DataAccessException;
import exception.DataWriteException;
import model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> findAll();
    Product findById(int id) throws DataAccessException;
    List<Product> findByName(String name) throws DataAccessException;
    List<Product> findByCategoryName(String name) throws DataAccessException;
    List<Product> findByCategoryId(int id) throws DataAccessException;
    Product create(String name, String description, int categoryId, int supplierId, int price) throws DataWriteException;
    void update(int id, String name, String description, int categoryId, int supplierId, int price) throws DataWriteException;
}
