package dao;

import entity.ProductCategory;
import exception.DataAccessException;

import java.util.List;

public interface ProductCategoryDao {
    ProductCategory findById(int id) throws DataAccessException;

    List<ProductCategory> findByName(String name) throws DataAccessException;
}

