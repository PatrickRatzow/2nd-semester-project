package controller;

import dao.ProductDao;
import datasource.DBManager;
import datasource.DataAccessException;
import model.Product;
import model.Specification;

import java.util.function.Consumer;

public class ProductController {
    public void getBySpecification(Specification specification, Consumer<Product> productConsumer) {
        DBManager.getInstance().getConnectionThread(conn -> {
            final ProductDao dao = conn.getDaoFactory().createProductDao();
            try {
                final Product product = dao.findBySpecification(specification);
                productConsumer.accept(product);
            } catch (DataAccessException e) {
                productConsumer.accept(null);
            }
        }).start();
    }
}
