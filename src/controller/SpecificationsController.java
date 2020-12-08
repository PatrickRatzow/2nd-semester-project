package controller;

import model.Product;
import model.Specification;
import model.specifications.Roof;
import model.specifications.Window;
import util.validation.Validator;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class SpecificationsController {
    private final ProductController productController;
    private final List<Consumer<List<Specification>>> onFindListeners = new LinkedList<>();
    private final List<Consumer<OrderController>> onSaveListeners = new LinkedList<>();
    private final List<Consumer<Exception>> onErrorListeners = new LinkedList<>();
    private final Map<Integer, SpecificationController> specificationControllers = new HashMap<>();

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
    
    public void addSpecificationController(SpecificationController specificationController) {
        int id = specificationController.getDisplayId();
        if (id == 0) {
            specificationController.setDisplayId(specificationControllers.size() + 1);
        }
        specificationControllers.put(specificationController.getDisplayId(), specificationController);
    }
    
    public Collection<SpecificationController> getSpecificationControllers() {
        return specificationControllers.values();
    }
    
    public void getSpecifications() {
        final List<Specification> specifications = new LinkedList<>();
        specifications.add(new Window());
        specifications.add(new Roof());
    
        onFindListeners.forEach(l -> l.accept(specifications));
    }
    
    public void getProductsFromSpecifications() {
        new Thread(() -> {
            Collection<SpecificationController> specificationControllersCopy = specificationControllers.values();
            int size = specificationControllersCopy.size();
            Map<SpecificationController, Product> specProductsMap = new HashMap<>();
            
            AtomicInteger atomicInteger = new AtomicInteger();
            Validator validator = new Validator();
            for (SpecificationController specificationController : specificationControllersCopy) {
                final Specification spec = specificationController.getSpecification();
                productController.getBySpecification(spec, p -> {
                    if (p == null) {
                        validator.addError("Unable to find product for " + specificationController.getDisplayName());
                    } else {
                        specProductsMap.put(specificationController, p);
                    }
    
                    int responseAmount = atomicInteger.incrementAndGet();
                    if (responseAmount == size) {
                        if (validator.hasErrors()) {
                            onErrorListeners.forEach(l -> l.accept(validator.getCompositeException()));
                        } else {
                            OrderController orderController = new OrderController();
                            for (Entry<SpecificationController, Product> entry : specProductsMap.entrySet()) {
                                SpecificationController specController = entry.getKey();
                                Product product = entry.getValue();
    
                                orderController.addProduct(product, specController.getResultAmount(),
                                        specController.getDisplayName());
                            }
    
                            onSaveListeners.forEach(l -> l.accept(orderController));
                        }
                    }
                });
            }
        }).start();
    }
}
