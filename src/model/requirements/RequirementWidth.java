package model.requirements;

import model.Requirement;

public class RequirementWidth extends Requirement<Integer> {
    @Override
    public String getName() {
        return "Width";
    }

    @Override
    public String getDescription() {
        return "The width of the window";
    }

    @Override
    public String getId() {
        return "width";
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
