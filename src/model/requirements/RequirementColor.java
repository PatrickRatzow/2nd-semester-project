package model.requirements;

import model.Requirement;

import java.awt.*;

public class RequirementColor extends Requirement<Color> {
    @Override
    public String getName() {
        return "Color";
    }

    @Override
    public String getDescription() {
        return "The color of the window";
    }

    @Override
    public String getId() {
        return "color";
    }

    @Override
    public String getSQLKey() {
        return getId();
    }

    @Override
    public String getSQLValue() {
        return "#ffffff";
    }

    @Override
    public void setValueFromSQLValue(String sql) {
        setValue(Color.BLACK);
    }
}
