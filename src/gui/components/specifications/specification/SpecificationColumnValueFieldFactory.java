package gui.components.specifications.specification;

import model.Requirement;
import model.requirements.RequirementColor;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

public class SpecificationColumnValueFieldFactory {
	private static final List<ValueFieldCheck> checks = new LinkedList<>();
	
	private static class ValueFieldCheck {
		private final Class<? extends Requirement> requirement;
		private final Callable<SpecificationColumnValueField<?, ?, ?>> field;
		
		public ValueFieldCheck(Class<? extends Requirement> requirement, Callable<SpecificationColumnValueField<?, ?, ?>> field) {
			this.requirement = requirement;
			this.field = field;
		}
		
		public boolean equalsRequirement(Requirement requirement) {
			return requirement.getClass().equals(this.requirement);
		}
		
		public SpecificationColumnValueField<?, ?, ?> getField() {
			try {
				return field.call();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return null;
		}
	}
	
	static {
		checks.add(new ValueFieldCheck(RequirementColor.class, () -> {
			SpecificationColumnColor field = new SpecificationColumnColor();
			List<String> options = new LinkedList<>();
			options.add("Roed");
			options.add("Blaa");
			options.add("Groen");
			options.add("Lilla");
			field.setOptions(options);
			field.setValue(options.get(0));
			
			return field;
		}));
	}
	
    public static SpecificationColumnValueField<?, ?, ?> create(Requirement requirement) {
    	SpecificationColumnValueField<?, ?, ?> field = null;
	
		Iterator<ValueFieldCheck> iterator = checks.iterator();
		while (iterator.hasNext() && field == null) {
			ValueFieldCheck check = iterator.next();
			if (check.equalsRequirement(requirement)) {
				field = check.getField();
			}
		}
		
		if (field == null) {
			field = new SpecificationColumnTextField();
		}
		
		return field;
    }
}
