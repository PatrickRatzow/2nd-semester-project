package controller;

import entity.Specification;
import entity.specifications.Roof;
import entity.specifications.Window;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class SpecificationsController {
    
	private List<Consumer<List<Specification>>> onFindListeners = new LinkedList<>();
	private List<Consumer<Specification>> onSaveListeners = new LinkedList<>();
	private ProjectController projectController;
	
	public SpecificationsController(ProjectController projectController) {
		this.projectController = projectController;
	}
    
	public void addFindListener(Consumer<List<Specification>> listener) {
		onFindListeners.add(listener);
	}
	
	public void addSaveListener(Consumer<Specification> listener) {
		onSaveListeners.add(listener);
	}
	
	
    public void getSpecifications() {
        final List<Specification> specifications = new LinkedList<>();
        specifications.add(new Window());
        specifications.add(new Roof());
        
        onFindListeners.forEach(l -> l.accept(specifications));
    }
    
    
}
