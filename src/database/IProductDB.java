package database;

import model.DataAccessException;
import model.DataWriteException;
import model.Product;

import java.sql.SQLException;
import java.util.List;

public interface IProductDB {
    List<Product> findAll();
    Product findById(int id) throws DataAccessException;
    Product findByName(String name) throws DataAccessException;
    List<Product> findByCategoryName(String name) throws DataAccessException;
    Product create(String name, String description, int categoryId, int supplierId, int price) throws DataWriteException;
    void update(int id, String name, String description, int categoryId, int supplierId, int price) throws DataWriteException;
}
