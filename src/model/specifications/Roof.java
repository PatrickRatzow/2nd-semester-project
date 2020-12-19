package model.specifications;

import model.Requirement;
import model.Specification;
import model.requirements.RequirementColor;

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
    public String getId() {
        return "roof";
    }

    @Override
    public String getName() {
        return "Tag";
    }
}
