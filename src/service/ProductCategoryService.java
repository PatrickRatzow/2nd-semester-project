package service;

import exception.DataAccessException;
import entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    ProductCategory findById(final int id) throws DataAccessException;
    List<ProductCategory> findByName(final String name) throws DataAccessException;
}
