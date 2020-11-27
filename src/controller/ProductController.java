package controller;

import dao.ProductDao;
import datasource.DBConnection;
import datasource.DBManager;
import entity.Product;
import exception.DataAccessException;

import java.util.List;

public class ProductController {
        /*
    public List<Product> findAll() {
        return productDB.findAll();
    }

    public Product findById(int id) throws DataAccessException {
        return productDB.findById(id);
    }*/

    public List<Product> findByName(String name) throws DataAccessException {
        DBConnection connection = DBManager.getPool().getConnection();
        ProductDao productDao = DBManager.getDaoFactory().createProductDao(connection);
        List<Product> products = productDao.findByName(name);

        connection.release();

        return products;
    }
    /*
    public List<Product> findByCategoryName(String name) throws DataAccessException {
        return productDB.findByCategoryName(name);
    }

    public List<Product> findByCategoryId(int id) throws DataAccessException {
        return productDB.findByCategoryId(id);
    }

    public Product create(Product product, ProductCategory category, Supplier supplier) throws IllegalArgumentException, DataWriteException {
        // FIXME: Yeh we need actual verification or something
        if (category == null) {
            throw new IllegalArgumentException("No category set");
        }
        if (supplier == null) {
            throw new IllegalArgumentException("No supplier set");
        }

        return productDB.create(product.getName(), product.getDesc(), category.getId(), supplier.getId(), product.getPrice().getAmount());
    }

    public void update(Product product, ProductCategory category, Supplier supplier) throws IllegalArgumentException, DataWriteException {
        // FIXME: Yeh we need actual verification or something
        if (category == null) {
            throw new IllegalArgumentException("No category set");
        }
        if (supplier == null) {
            throw new IllegalArgumentException("No supplier set");
        }
        if (product.getId() == 0) {
            throw new IllegalArgumentException("Category does not have a valid ID set on it");
        }

        productDB.update(product.getId(), product.getName(), product.getDesc(), category.getId(), supplier.getId(), product.getPrice().getAmount());
    }

     */
}
