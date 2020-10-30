package controller;

import model.*;

import java.util.Collection;
import java.util.List;

/**
 * The type Product price calculator controller.
 */
public class ProductPriceCalculatorController {
    /**
     * The Product controller.
     */
    private ProductController productController = new ProductController();
    private CheapestProduct cheapest;

    public synchronized void updateCheapest(Product product, Price price, int quantity) {
        // If we don't have a product at all, just set the first product to cheapest
        if (cheapest == null) {
            cheapest = new CheapestProduct(product, price, quantity);

            return;
        }
        // If the difference is 0 or below that means the new product isn't cheaper
        if (cheapest.getProduct().getPrice().compareTo(price) <= 0) return;

        cheapest = new CheapestProduct(product, price, quantity);
    }

    private Product findCheapestProduct(Specification specification, List<Product> products) {
        // Reset cheapest
        cheapest = null;

        // TODO: Threads n stuff
        final int size = products.size();
        for (int i = 0; i < size; i++) {
            Product product = products.get(i);
            if (!specification.isValid(product)) continue;
            specification.setProduct(product);
            Price price = specification.getPrice();
            int quantity = specification.getQuantity();

            updateCheapest(product, price, quantity);
        }

        return cheapest.getProduct();
    }
}
