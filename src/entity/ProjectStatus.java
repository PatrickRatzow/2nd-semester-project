package entity;

public enum ProjectStatus {
    ON_HOLD(0),
    IN_PROGRESS(1),
    AWAITING_PAYMENT(2),
    FINISHED(3);

    private final int value;

    ProjectStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
