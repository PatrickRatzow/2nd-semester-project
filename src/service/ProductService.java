package service;

import entity.Product;
import exception.DataAccessException;
import exception.DataWriteException;

public interface ProductService {
    Product findById(int id) throws DataAccessException;
}
