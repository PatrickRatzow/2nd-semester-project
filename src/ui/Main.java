package ui;

import controller.ProductPriceCalculatorController;
import database.DBConnection;

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
        DBConnection.getInstance();
        System.out.println("Here");
    }
}
