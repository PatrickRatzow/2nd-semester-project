package ui;

import controller.ProductPriceCalculatorController;
import database.DBConnection;
import model.*;

import java.util.*;

/**
 * The type Main.
 */
public class Main {
    private static ProductPriceCalculatorController priceCalculatorController = new ProductPriceCalculatorController();
    private static Scanner scanner;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // Start time for benchmark
        long startTime = System.nanoTime();

        List<Product> products = new ArrayList<>();
        Random random = new Random();
        // Ensure a need seed every run
        random.setSeed(startTime);
        for (int i = 0; i < 1500; i++) {
            Product product = new Product(String.valueOf(i), "", new Price(random.nextInt(1500000)));
            products.add(product);
        }

        Specification spec = new Roof();
        CheapestProduct cheapestProduct = priceCalculatorController.findCheapestProduct(spec, products);


        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1e6;

        System.out.println("Took " + duration + "ms to find cheapest product");
        System.out.println("--------Cheapest Product-------");
        System.out.println(cheapestProduct);
    }
}
