package dao;

import exception.DataAccessException;
import model.Product;
import model.Specification;

import java.util.List;

public interface ProductDao {
    List<Product> findAll() throws DataAccessException;

    Product findById(int id) throws DataAccessException;

    List<Product> findByIds(List<Integer> ids) throws DataAccessException;

    List<Product> findByName(String name) throws DataAccessException;
    
    Product findBySpecification(Specification specification) throws DataAccessException;
}
