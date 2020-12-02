package entity;

import java.util.List;

/**
 * The type Product type.
 */
public abstract class Specification {
    private List<Requirement> requirements;
    public abstract Specification clone();

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
    }
}
