package persistence.repository;

import exception.DataAccessException;
import model.Product;

public interface ProductRepository {
    Product findById(int id) throws DataAccessException;
}
