package dao;

import entity.Product;
import entity.Specification;
import exception.DataAccessException;

import java.util.List;

public interface SpecificationToProductCategoryDao {
    List<Product> findBySpecificationId(Specification specification) throws DataAccessException;
}
