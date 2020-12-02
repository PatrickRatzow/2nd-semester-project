package entity.specifications;

import entity.Requirement;
import entity.Specification;
import entity.requirements.RequirementColor;
import entity.requirements.RequirementHeight;
import entity.requirements.RequirementWidth;

import java.util.LinkedList;
import java.util.List;

public class Window extends Specification {
    public Window() {
        super();

        List<Requirement> requirements = new LinkedList<>();
        requirements.add(new RequirementColor());
        requirements.add(new RequirementWidth());
        requirements.add(new RequirementHeight());

        setRequirements(requirements);
    }

    @Override
    public Specification clone() {
        return new Window();
    }

    @Override
    public String getId() {
        return "window";
    }

    @Override
    public String getName() {
        return "Window";
    }
}
