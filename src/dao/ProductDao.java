package dao;

import entity.Product;
import entity.Specification;
import exception.DataAccessException;

import java.util.List;

public interface ProductDao {
    List<Product> findAll() throws DataAccessException;

    Product findById(int id) throws DataAccessException;

    List<Product> findByIds(List<Integer> ids) throws DataAccessException;

    List<Product> findByName(String name) throws DataAccessException;
    
    List<Product> findBySpecification(Specification specification) throws DataAccessException;
}
