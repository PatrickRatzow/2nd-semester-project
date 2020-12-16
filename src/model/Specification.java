package model;

import util.validation.Validatable;
import util.validation.Validator;

import java.util.List;

/**
 * The type Product type.
 */
public abstract class Specification implements Validatable {
    private List<Requirement> requirements;
    public abstract String getId();
    public abstract String getName();

    @Override
    public void validate() throws Exception {
        Validator validator = new Validator();
        for (Requirement requirement : requirements) {
            validator.addValidatable(requirement);
        }
        
        if (validator.hasErrors()) {
            throw validator.getCompositeException();
        }
    }
    
    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
    }
}
