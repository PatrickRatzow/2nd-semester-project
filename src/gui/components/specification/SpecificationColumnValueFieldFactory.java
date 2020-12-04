package gui.components.specification;

import entity.Requirement;

import java.util.LinkedList;
import java.util.List;

public class SpecificationColumnValueFieldFactory {
	static SpecificationColumnValueField<?, ?, ?> create(Requirement requirement) {
		System.out.println(requirement.getClass().getName());
		switch (requirement.getClass().getName()) {
			case "entity.requirements.RequirementColor":
				System.out.println("I'M IN");
				SpecificationColumnColor field = new SpecificationColumnColor();
				List<String> options = new LinkedList<String>();
				options.add("Roed");
				options.add("Blaa");
				options.add("Groen");
				options.add("Lilla");
				field.setOptions(options);
				field.setValue(options.get(0));
				
				return field;
		
			default:
				return new SpecificationColumnTextField();
		}
	}
}
