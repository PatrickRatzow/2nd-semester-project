package dao;

import exception.DataAccessException;
import model.ProductCategory;

import java.util.List;

public interface ProductCategoryDao {
    ProductCategory findById(int id) throws DataAccessException;

    List<ProductCategory> findByName(String name) throws DataAccessException;
}

