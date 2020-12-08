package controller;

import model.Requirement;
import model.Specification;
import util.validation.Validator;
import util.validation.rules.EmptyValidationRule;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class SpecificationController {
    private int displayId;
    private String displayName;
    private int resultAmount;
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
        Validator validator = new Validator();
        validator.addRule(new EmptyValidationRule(displayName, "Navn må ikke være tom!"));
        validator.addValidatable(specification);
        
        if (validator.hasErrors()) {
            throw validator.getCompositeException();
        }
        
        onSaveListeners.forEach(l -> l.accept(this));
    }
    
    public void setDisplayName(String name) {
        displayName = name;
    }

    public String getDisplayName() {
        return displayName;
    }
    
    public String getName() {
        return specification.getName();
    }

    public void setResultAmount(int amount) {
        resultAmount = amount;
    }

    public int getResultAmount() {
        return resultAmount;
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
