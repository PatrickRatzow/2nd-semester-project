package model.specifications;

import model.Requirement;
import model.Specification;
import model.requirements.RequirementColor;
import model.requirements.RequirementHeight;
import model.requirements.RequirementWidth;
import util.validation.Validator;
import util.validation.rules.EmptyValidationRule;

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

	@Override
	public void validate() throws Exception {
		Validator validator = new Validator();
		String parser = Integer.toString(getResultAmount());
		validator.addRule(new EmptyValidationRule(parser, "Antal skal vaere over 0!"));
	}
}
