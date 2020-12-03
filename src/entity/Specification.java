package entity;

import java.util.List;

/**
 * The type Product type.
 */
public abstract class Specification {
    private List<ProductCategory> categories;
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

    public List<ProductCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ProductCategory> categories) {
        this.categories = categories;
    }
    
    public void setDisplayName(String newDisplayName) {
    	this.displayName = newDisplayName;
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
