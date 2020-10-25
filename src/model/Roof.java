package model;

/**
 * The type Roof.
 */
public class Roof extends ProductType {
    @Override
    public Price getPrice() {
        Price price = new Price();

        price.setAmount(100 * 100);

        return price;
    }
}
