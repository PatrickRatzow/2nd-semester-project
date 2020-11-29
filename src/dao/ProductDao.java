package dao;

import entity.Product;
import exception.DataAccessException;

import java.util.List;

public interface ProductDao {
    List<Product> findAll();
    Product findById(int id) throws DataAccessException;
    List<Product> findByIds(List<Integer> ids) throws DataAccessException;
    List<Product> findByName(String name) throws DataAccessException;
}
