package database;

import model.ProductCategory;

import java.util.List;

public interface IProductCategoryDB {
    List<ProductCategory> findAll();
    ProductCategory findById(int id) throws DataAccessException;
    List<ProductCategory> findByName(String name) throws DataAccessException;
    ProductCategory create(String name, String desc) throws DataWriteException;
    void update(int id, String name, String desc) throws DataWriteException;
    void delete(int id) throws DataWriteException;
}

