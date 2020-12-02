package util;

import entity.CheapestProduct;
import entity.Specification;

import java.util.function.Consumer;


public class CheapestAlgorithm extends Thread {
    private final Specification specification;
    private final Consumer<CheapestProduct> callback;

    public CheapestAlgorithm(Specification specification, Consumer<CheapestProduct> callback) {
        this.specification = specification;
        this.callback = callback;
    }

    @Override
    public void run() {
        /*
        Product product = specification.getProduct();
        Price price = specification.getPrice();
        int quantity = specification.getQuantity();
        CheapestProduct cheapestProduct = new CheapestProduct(product, price, quantity);

        // Lets pretend our mighty algorithm actually takes time to run!
        try {
            sleep(new Random().nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.callback.accept(cheapestProduct);

         */
    }
}
