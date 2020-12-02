package entity.requirements;

import entity.Requirement;

public class RequirementWidth extends Requirement<Integer> {
    @Override
    public String getName() {
        return "Width";
    }

    @Override
    public String getDescription() {
        return "The width of the window";
    }
}
