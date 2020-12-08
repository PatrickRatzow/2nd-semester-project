package model;

import util.validation.Validatable;

public abstract class Requirement<T> implements Validatable {
    private T value;

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getId();

    public abstract String getSQLKey();

    public abstract String getSQLValue();

    public abstract void setValueFromSQLValue(String sql);

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
