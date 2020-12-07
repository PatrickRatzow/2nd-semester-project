package model.requirements;

import model.Requirement;

public class RequirementHeight extends Requirement<Integer> {
    @Override
    public String getName() {
        return "Height";
    }

    @Override
    public String getDescription() {
        return "The height of the window";
    }

    @Override
    public String getId() {
        return "height";
    }

    @Override
    public String getSQLKey() {
        return getId();
    }

    @Override
    public String getSQLValue() {
        return String.valueOf(getValue());
    }

    @Override
    public void setValueFromSQLValue(String sql) {
        setValue(Integer.parseInt(sql));
    }
}
