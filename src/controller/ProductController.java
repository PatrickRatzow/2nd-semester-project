package controller;

import dao.ProductDao;
import datasource.DBConnection;
import datasource.DBManager;
import entity.Product;
import exception.DataAccessException;

import java.util.List;

public class ProductController {
    public List<Product> findByName(String name) throws DataAccessException {
        final DBConnection connection = DBManager.getPool().getConnection();
        final ProductDao productDao = DBManager.getDaoFactory().createProductDao(connection);
        final List<Product> products = productDao.findByName(name);

        connection.release();

        return products;
    }
}
