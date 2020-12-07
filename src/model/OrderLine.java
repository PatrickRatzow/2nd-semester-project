package model;

public class OrderLine {
    private Product product;
    private int quantity;
    private String displayName;

    public OrderLine() {
    }

    public OrderLine(Product product, int quantity, String displayName) {
        this.product = product;
        this.quantity = quantity;
        this.displayName = displayName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
