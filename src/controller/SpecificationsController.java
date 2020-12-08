package controller;

import model.Product;
import model.Specification;
import model.specifications.Roof;
import model.specifications.Window;
import util.validation.Validator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class SpecificationsController {
    private final ProductController productController;
    private final List<Consumer<List<Specification>>> onFindListeners = new LinkedList<>();
    private final List<Consumer<OrderController>> onSaveListeners = new LinkedList<>();
    private final List<Consumer<Exception>> onErrorListeners = new LinkedList<>();
    private List<Specification> specifications = new LinkedList<>();

    public SpecificationsController() {
        this(new ProductController());
    }
    public SpecificationsController(ProductController productController) {
        this.productController = productController;
    }
    
    public void addFindListener(Consumer<List<Specification>> listener) {
        onFindListeners.add(listener);
    }

    public void addSaveListener(Consumer<OrderController> listener) {
        onSaveListeners.add(listener);
    }
    
    public void addErrorListener(Consumer<Exception> listener) {
        onErrorListeners.add(listener);
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

    public void getProductsFromSpecifications() {
        new Thread(() -> {
            List<Specification> specificationsCopy = specifications;
            int size = specifications.size();
            Map<Specification, Product> specProductsMap = new HashMap<>();
            
            AtomicInteger atomicInteger = new AtomicInteger();
            Validator validator = new Validator();
            for (Specification specification : specificationsCopy) {
                final Specification specTemp = specification;
                productController.getBySpecification(specTemp, p -> {
                    if (p == null) {
                        validator.addError("Unable to find product for " + specification.getDisplayName());
                    } else {
                        specProductsMap.put(specTemp, p);
                    }
    
                    int responseAmount = atomicInteger.incrementAndGet();
                    if (responseAmount == size) {
                        if (validator.hasErrors()) {
                            onErrorListeners.forEach(l -> l.accept(validator.getCompositeException()));
                        } else {
                            OrderController orderController = new OrderController();
                            for (Entry<Specification, Product> entry : specProductsMap.entrySet()) {
                                Specification spec = entry.getKey();
                                Product product = entry.getValue();
    
                                orderController.addProduct(product, spec.getResultAmount(), spec.getDisplayName());
                            }
    
                            onSaveListeners.forEach(l -> l.accept(orderController));
                        }
                    }
                });
            }
        }).start();
    }
}
