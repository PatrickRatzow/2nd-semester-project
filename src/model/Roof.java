package model;

/**
 * The type Roof.
 */
// https://www.roofingmegastore.co.uk/blog/how-to-calculate-how-many-tiles-you-need-for-your-roof.html
// https://roofstores.co.uk/guides/tiles-and-slates/calculating-amount-roof-tiles-slates/
public class Roof extends Specification {
    private float getTilesNeededPerRow() {
        return 0.0f;
    }

    @Override
    public boolean isValid(Product product) {
        return product.equals("Tagsten");
    }

    @Override
    public Price getPrice() {
        Price price = new Price();

        price.setAmount(getQuantity() * (100 * 100));

        return price;
    }
}
