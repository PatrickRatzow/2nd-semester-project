package gui.components.specifications.specification;

import entity.Requirement;

import java.util.LinkedList;
import java.util.List;

//Can also make drop down menus for other requirements here:
public class SpecificationColumnValueFieldFactory {
	static SpecificationColumnValueField<?, ?, ?> create(Requirement requirement) {
		switch (requirement.getClass().getName()) {
			case "entity.requirements.RequirementColor":
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
