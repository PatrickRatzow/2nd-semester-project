package ui;

import controller.ProductController;
import controller.ProductPriceCalculatorController;
import model.Product;

import java.util.List;
import java.util.Scanner;

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
    public static void main(String[] args) {
        ProductController productController = new ProductController();
        List<Product> products = productController.findAll();
        //priceCalculatorController.findAll();

        List<Product> products1 = productController.findAll();
        System.out.println("YEh");
    }
}
