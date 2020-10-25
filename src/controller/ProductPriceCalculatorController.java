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
    ProductController productController = new ProductController();

    private Product findCheapestProduct(Specification specification, Collection<Product> products) {
        // TODO: Threads n stuff
        final int size = products.size();
        for (int i = 0; i < size; i++) {

        }

        return null;
    }
}
