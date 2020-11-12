package controller;

import model.CheapestProduct;
import model.Specification;
import model.product.Product;
import model.product_category.ProductCategory;
import util.CheapestAlgorithm;

import java.util.ArrayList;
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

    private synchronized void updateCheapest(CheapestProduct cheapestProduct) {
        // If we don't have a product, just set the first product to cheapest
        if (cheapest == null) {
            cheapest = cheapestProduct;

            return;
        }
        // If the difference is 0 or below that means the new product isn't cheaper
        if (cheapest.getPrice().compareTo(cheapestProduct.getPrice()) <= 0) return;

        cheapest = cheapestProduct;
    }

    public CheapestProduct findCheapestProduct(Specification specification, List<ProductCategory> categories) throws InterruptedException {
        // Reset cheapest
        cheapest = null;
        // We need a list of our threads to later join them
        final List<Thread> threads = new ArrayList<>();

        final int categorySize = categories.size();
        for (int i = 0; i < categorySize; i++) {
            ProductCategory category = categories.get(i);
            List<Product> products = category.getProducts();
            final int size = products.size();
            for (int j = 0; i < size; i++) {
                Product product = products.get(j);
                specification.setProduct(product);
                // For testing we have this locked at 1
                specification.setQuantity(1);
                // Supply the specification + our consumer
                Thread thread = new CheapestAlgorithm(specification, this::updateCheapest);
                threads.add(thread);
            }
        }

        /*
         * It's actually more performant to start the threads in a separate for loop.
         *
         * This is due to the fact that we will have a lot less interrupts on the main thread
         * when our callback comes back from a worker thread, as by now our main thread is doing nothing;
         * while it would be busy populating the threads List if we started the threads in the previous for loop.
         */
        for (Thread t : threads) {
            t.start();
        }

        // Wait for all threads to finish
        for (Thread t : threads) {
            t.join();
        }

        return cheapest;
    }
}
