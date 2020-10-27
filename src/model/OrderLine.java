package model;

public class OrderLine {
    private int quantity;

    public OrderLine() {
    }

    public OrderLine(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
