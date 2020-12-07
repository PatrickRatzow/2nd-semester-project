package model;

import java.util.List;

/**
 * The type Product type.
 */
public abstract class Specification {
    private List<Requirement> requirements;
    public abstract Specification clone();
    public abstract String getId();
    public abstract String getName();
    private String displayName;
    private int resultAmount;

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
