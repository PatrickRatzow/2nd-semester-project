package controller;

import dao.ProductDao;
import datasource.DBManager;
import datasource.DataAccessException;
import model.Product;
import model.Specification;
import util.ConnectionThread;

import java.util.function.Consumer;

public class ProductController {
    public void getBySpecification(Specification specification, Consumer<Product> productConsumer) {
        new ConnectionThread(conn -> {
            final ProductDao dao = DBManager.getDaoFactory().createProductDao(conn);
            try {
                final Product product = dao.findBySpecification(specification);
                productConsumer.accept(product);
            } catch (DataAccessException e) {
                productConsumer.accept(null);
            }
        }).start();
    }
}
