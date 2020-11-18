package model;

public enum OrderStatus {
    AWAITING(0),
    IN_PROGRESS(1),
    FINISHED(2);

    private final int value;
    private OrderStatus(int value) {
        this.value = value;
    }
    public int getValue() {
        return this.value;
    }
}

