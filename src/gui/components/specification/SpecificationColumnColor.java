package gui.components.specification;

import javax.swing.*;
import java.util.Collection;

public class SpecificationColumnColor implements SpecificationColumnValueField<String, JComboBox<String>, Collection<String>> {
	private JComboBox<String> component;
	
	public SpecificationColumnColor() {
		component = new JComboBox<String>();
	}
	
	@Override
	public String getValue() {
		return (String) component.getSelectedItem();
	}

	@Override
	public void setValue(String value) {
		component.setSelectedItem(value);
	}

	@Override
	public void setOptions(Collection<String> options) {
		for (String option : options) {
			component.addItem(option);
		}
	}
	
	@Override
	public String getValueAsString() {
		return getValue();
	}
	
	@Override
	public JComboBox<String> getComponent() {
		return component;
	}
}
