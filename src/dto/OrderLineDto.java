package dto;

public class OrderLineDto {
    private final int orderId;
    private final int productId;
    private final int quantity;

    public OrderLineDto(final int id, final int productId, final int quantity) {
        this.orderId = id;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
