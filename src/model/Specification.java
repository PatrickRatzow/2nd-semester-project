package model;

import util.validation.Validatable;
import util.validation.Validator;
import util.validation.rules.EmptyValidationRule;

import java.util.List;

/**
 * The type Product type.
 */
public abstract class Specification implements Validatable {
    private List<Requirement> requirements;
    public abstract Specification clone();
    public abstract String getId();
    public abstract String getName();
    private String displayName;
    private int resultAmount;

    @Override
    public void validate() throws Exception {
        Validator validator = new Validator();
        validator.addRule(new EmptyValidationRule(displayName, "Navn må ikke være tom!"));
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
    
    public void setDisplayName(String displayName) {
    	this.displayName = displayName;
    }
    
    public String getDisplayName() {
    	return displayName;
    }
    
    public void setResultAmount(int newResultAmount) {
    	this.resultAmount = newResultAmount;
    }
    
    public int getResultAmount() {
    	return resultAmount;
    }
}
