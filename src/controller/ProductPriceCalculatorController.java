package controller;

import model.Product;

import java.util.Collection;

/**
 * The type Product price calculator controller.
 */
public class ProductPriceCalculatorController {
    /**
     * The Product controller.
     */
    ProductController productController = new ProductController();

    public Collection<Product> findAll() {
        return productController.findAll();
    }
}
