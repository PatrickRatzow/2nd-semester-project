package controller;

import exception.DataAccessException;
import exception.DataWriteException;
import model.DBConnection;
import model.Product;
import model.ProductCategory;
import persistance.ProductCategoryDao;
import persistance.mssql.ProductCategoryDaoMsSql;

import java.sql.SQLException;
import java.util.List;

public class ProductCategoryController {
    ProductCategoryDao productCategoryDB = new ProductCategoryDaoMsSql();
    ProductController productController = new ProductController();

    public List<ProductCategory> findAll(boolean populateProducts) throws SQLException, DataAccessException {
        if (!populateProducts) {
            return productCategoryDB.findAll();
        }

        DBConnection conn = DBConnection.getInstance();
        List<ProductCategory> categories;
        try {
            conn.startTransaction();

            categories = productCategoryDB.findAll();
            for (ProductCategory category : categories) {
                populateProducts(category);
            }

            conn.commitTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollbackTransaction();

                throw e;
            } catch (SQLException re) {
                re.printStackTrace();

                throw e;
            }
        }

        return categories;
    }

    public List<ProductCategory> findByName(String name, boolean populateProducts) throws DataAccessException, SQLException {
        if (!populateProducts) {
            return productCategoryDB.findByName(name);
        }

        DBConnection conn = DBConnection.getInstance();
        List<ProductCategory> categories;
        try {
            conn.startTransaction();

            categories = productCategoryDB.findByName(name);
            for (ProductCategory category : categories) {
                populateProducts(category);
            }

            conn.commitTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollbackTransaction();

                throw e;
            } catch (SQLException re) {
                re.printStackTrace();

                throw e;
            }
        }

        return categories;
    }

    public ProductCategory findById(int id, boolean populateProducts) throws DataAccessException, SQLException {
        if (!populateProducts) {
            return productCategoryDB.findById(id);
        }

        DBConnection conn = DBConnection.getInstance();
        ProductCategory category;
        try {
            conn.startTransaction();

            category = productCategoryDB.findById(id);
            populateProducts(category);

            conn.commitTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollbackTransaction();

                throw e;
            } catch (SQLException re) {
                re.printStackTrace();

                throw e;
            }
        }

        return category;
    }

    public ProductCategory create(ProductCategory category) throws DataWriteException {
        return productCategoryDB.create(category.getName(), category.getDesc());
    }

    public void update(ProductCategory category) throws DataWriteException, IllegalArgumentException {
        if (category.getId() == 0) {
            throw new IllegalArgumentException("Category does not have any id set on it");
        }

        productCategoryDB.update(category.getId(), category.getName(), category.getDesc());
    }

    public void delete(ProductCategory category) throws DataWriteException, IllegalArgumentException {
        if (category.getId() == 0) {
            throw new IllegalArgumentException("Category does not have any id set on it");
        }

        productCategoryDB.delete(category.getId());
    }

    private void populateProducts(ProductCategory category) throws DataAccessException {
        List<Product> products = productController.findByCategoryId(category.getId());
        // Loop over each product and call the addProduct method with it
        products.forEach(category::addProduct);
    }
}
