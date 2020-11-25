package dao;

import dto.ProductCategoryDto;
import exception.DataAccessException;
import exception.DataWriteException;
import entity.ProductCategory;

import java.util.List;

public interface ProductCategoryDao {
    List<ProductCategoryDto> findAll();
    ProductCategoryDto findById(int id) throws DataAccessException;
    List<ProductCategoryDto> findByName(String name) throws DataAccessException;
    ProductCategory create(String name, String desc) throws DataWriteException;
    void update(int id, String name, String desc) throws DataWriteException;
    void delete(int id) throws DataWriteException;
}

