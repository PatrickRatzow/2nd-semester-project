package dao;

import datasource.DataAccessException;
import model.Product;
import model.Specification;

import java.util.List;

public interface ProductDao {
    List<Product> findByIds(List<Integer> ids) throws DataAccessException;
    
    Product findBySpecification(Specification specification) throws DataAccessException;
}
