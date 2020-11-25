package entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The type Product type.
 */
public abstract class Specification {
    private Map<String, Object> params = new HashMap<>();
    private Set<String> categories = new HashSet<>();
    private Product product;
    private int quantity;
    public abstract Specification clone();
    public abstract boolean isValid(Product product);
    public abstract Price getPrice();

    /**
     * Sets param.
     *
     * @param key   the key
     * @param value the value
     */
    public void setParam(String key, String value) {
        params.put(key, value);
    }

    /**
     * Sets param.
     *
     * @param key   the key
     * @param value the value
     */
    public void setParam(String key, int value) {
        params.put(key, value);
    }

    /**
     * Sets param.
     *
     * @param key   the key
     * @param value the value
     */
    public void setParam(String key, float value) {
        params.put(key, value);
    }

    /**
     * Gets param.
     *
     * @param key the key
     * @return the param
     */
    public Object getParam(String key) {
        return params.get(key);
    }

    /**
     * Add category.
     *
     * @param type the type
     */
    public void addCategory(String type) {
        categories.add(type);
    }

    /**
     * Has category boolean.
     *
     * @param type the type
     * @return the boolean
     */
    public boolean hasCategory(String type) {
        return categories.contains(type);
    }

    /**
     * Gets categories.
     *
     * @return the categories
     */
    public Set<String> getCategories() {
        return categories;
    }

    /**
     * Gets product.
     *
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets product.
     *
     * @param product the product
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets quantity.
     *
     * @param quantity the quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
