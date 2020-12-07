package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Product category.
 */
public class ProductCategory {
    private int id;
    private String name;
    private String desc;
    private final Map<Integer, Product> products = new HashMap<>();

    public ProductCategory() {

    }

    public ProductCategory(int id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets desc.
     *
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Sets desc.
     *
     * @param desc the desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products.values());
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public void removeProduct(Product product) {
        products.remove(product.getId());
    }
}
