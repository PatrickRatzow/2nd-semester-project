package model;

public enum OrderStatus {
    AWAITING(1),
    IN_PROGRESS(2),
    FINISHED(3);

    private final int value;
    private OrderStatus(int value) {
        this.value = value;
    }
    public int getValue() {
        return this.value;
    }
}

