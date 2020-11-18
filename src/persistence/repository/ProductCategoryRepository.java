package persistence.repository;

import exception.DataAccessException;
import model.ProductCategory;

import java.util.List;

public interface ProductCategoryRepository {
    ProductCategory findById(final int id) throws DataAccessException;
    List<ProductCategory> findByName(final String name) throws DataAccessException;
}
