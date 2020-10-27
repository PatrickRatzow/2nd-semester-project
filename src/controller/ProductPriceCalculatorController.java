package controller;

import model.Product;
import model.Specification;

import java.util.Collection;

/**
 * The type Product price calculator controller.
 */
public class ProductPriceCalculatorController {
    /**
     * The Product controller.
     */
    private ProductController productController = new ProductController();
    private int cheapest;

    public synchronized int getCheapest() {
        return cheapest;
    }

    public synchronized void setCheapest(int cheapest) {
        this.cheapest = cheapest;
    }

    private Product findCheapestProduct(Specification specification, Collection<Product> products) {
        // Start at the highest value we can
        setCheapest(Integer.MAX_VALUE);

        // TODO: Threads n stuff
        final int size = products.size();
        for (int i = 0; i < size; i++) {

        }

        return null;
    }
}
