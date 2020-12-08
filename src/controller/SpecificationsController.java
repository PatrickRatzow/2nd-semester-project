package controller;

import dao.ProductDao;
import datasource.DBManager;
import exception.DataAccessException;
import model.Product;
import model.Specification;
import model.specifications.Roof;
import model.specifications.Window;
import util.ConnectionThread;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

public class SpecificationsController {
    private final List<Consumer<List<Specification>>> onFindListeners = new LinkedList<>();
    private final List<Consumer<OrderController>> onSaveListeners = new LinkedList<>();
    private List<Specification> specifications = new LinkedList<>();

    public void addFindListener(Consumer<List<Specification>> listener) {
        onFindListeners.add(listener);
    }

    public void addSaveListener(Consumer<OrderController> listener) {
        onSaveListeners.add(listener);
    }

    public void getSpecifications() {
        final List<Specification> specifications = new LinkedList<>();
        specifications.add(new Window());
        specifications.add(new Roof());

        onFindListeners.forEach(l -> l.accept(specifications));
    }

    public void setSpecifications(List<Specification> specifications) {
        this.specifications = specifications;
    }

    private Thread findProductsBySpecification(Specification spec, Consumer<Product> productConsumer) {
        return new ConnectionThread(conn -> {
            final ProductDao dao = DBManager.getDaoFactory().createProductDao(conn);
            try {
                final Product product = dao.findBySpecification(spec);

                productConsumer.accept(product);
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public void getProductsFromSpecifications() {
        new Thread(() -> {
            List<Specification> specificationsCopy = specifications;
            Map<Specification, Product> specProductsMap = new HashMap<>();
 
            List<Thread> threads = new LinkedList<>();
            for (Specification specification : specificationsCopy) {
                final Specification specTemp = specification;
                threads.add(findProductsBySpecification(specTemp, p -> specProductsMap.put(specTemp, p)));
            }

            for (Thread t : threads) {
                t.start();
            }

            for (Thread t : threads) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            OrderController orderController = new OrderController();
            for (Entry<Specification, Product> entry : specProductsMap.entrySet()) {
                Specification spec = entry.getKey();
                Product product = entry.getValue();
                
                orderController.addProduct(product, spec.getResultAmount(), spec.getDisplayName());
            }

            onSaveListeners.forEach(l -> l.accept(orderController));
        }).start();
    }
}
