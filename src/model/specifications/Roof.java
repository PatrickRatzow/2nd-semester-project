package model.specifications;

import model.Requirement;
import model.Specification;
import model.requirements.RequirementColor;
import util.validation.Validator;
import util.validation.rules.EmptyValidationRule;

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

	@Override
	public void validate() throws Exception {
		Validator validator = new Validator();
		String parser = Integer.toString(getResultAmount());
		validator.addRule(new EmptyValidationRule(parser, "Antal skal vaere over 0!"));
		
	}
}
