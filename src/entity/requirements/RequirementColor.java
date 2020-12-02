package entity.requirements;

import entity.Requirement;

public class RequirementColor extends Requirement<RequirementColor> {
    @Override
    public String getName() {
        return "Color";
    }

    @Override
    public String getDescription() {
        return "The color of the window";
    }
}
