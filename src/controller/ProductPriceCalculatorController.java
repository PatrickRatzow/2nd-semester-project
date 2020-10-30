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
    private Product cheapest;

    public synchronized void updateCheapest(Product product) {
        // If we don't have a product at all, just set the first product to cheapest
        if (cheapest == null) {
            cheapest = product;

            return;
        }
        if (product.getPrice().compareTo(cheapest.getPrice()) != 1) return;

        cheapest = product;
    }

    private Product findCheapestProduct(Specification specification, Collection<Product> products) {
        // Reset cheapest
        cheapest = null;

        // TODO: Threads n stuff
        final int size = products.size();
        for (int i = 0; i < size; i++) {

        }

        return null;
    }
}
