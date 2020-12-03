package entity.specifications;

import entity.Requirement;
import entity.Specification;
import entity.requirements.RequirementColor;

import java.util.LinkedList;
import java.util.List;

public class Roof extends Specification {
    public Roof() {
        super();

        List<Requirement> requirements = new LinkedList<>();
        requirements.add(new RequirementColor());

        setRequirements(requirements);
    }

    @Override
    public Specification clone() {
        return new Roof();
    }

    @Override
    public String getId() {
        return "roof";
    }

    @Override
    public String getName() {
        return "Roof";
    }
}
