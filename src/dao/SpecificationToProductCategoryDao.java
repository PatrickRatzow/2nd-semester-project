package dao;

import exception.DataAccessException;
import model.Product;
import model.Specification;

import java.util.List;

public interface SpecificationToProductCategoryDao {
    List<Product> findBySpecificationId(Specification specification) throws DataAccessException;
}
