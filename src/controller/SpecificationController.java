package controller;

import model.Requirement;
import model.Specification;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class SpecificationController {
    private int displayId;
    private final Specification specification;
    private final List<Consumer<SpecificationController>> onSaveListeners = new LinkedList<>();
    private final List<Consumer<Exception>> onErrorListeners = new LinkedList<>();
    
    public SpecificationController(Specification specification) {
        this.specification = specification;
    }

    public List<Requirement> getRequirements() {
        return specification.getRequirements();
    }

    public void setRequirements(List<Requirement> requirements) {
        specification.setRequirements(requirements);
    }

    public void addSaveListener(Consumer<SpecificationController> listener) {
        onSaveListeners.add(listener);
    }

    public void removeAllSaveListeners() {
        onSaveListeners.clear();
    }
    
    public void addErrorListener(Consumer<Exception> listener) {
        onErrorListeners.add(listener);
    }
    
    public void save() throws Exception {
        specification.validate();
        
        onSaveListeners.forEach(l -> l.accept(this));
    }
    
    public void setDisplayName(String name) {
        specification.setDisplayName(name);
    }

    public String getDisplayName() {
        return specification.getDisplayName();
    }

    public String getName() {
        return specification.getName();
    }

    public void setResultAmount(int amount) {
        specification.setResultAmount(amount);
    }

    public int getResultAmount() {
        return specification.getResultAmount();
    }

    public Specification getSpecification() {
        return specification;
    }

    public int getDisplayId() {
        return displayId;
    }

    public void setDisplayId(int displayId) {
        this.displayId = displayId;
    }
}
