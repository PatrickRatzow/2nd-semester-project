package entity.requirements;

import entity.Requirement;

public class RequirementHeight extends Requirement<Integer> {
    @Override
    public String getName() {
        return "Height";
    }

    @Override
    public String getDescription() {
        return "The height of the window";
    }
}
