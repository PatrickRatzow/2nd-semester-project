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
}
