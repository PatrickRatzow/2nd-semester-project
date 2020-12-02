package entity;

public abstract class Requirement<T> {
    private T value;
    public abstract String getName();
    public abstract String getDescription();

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String toString() {
        return getName() + " - " + getDescription();
    }
}
