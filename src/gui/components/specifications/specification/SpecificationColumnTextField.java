package gui.components.specifications.specification;

import javax.swing.*;

public class SpecificationColumnTextField implements SpecificationColumnValueField<String, JTextField, Void> {
	private JTextField component;
	
	public SpecificationColumnTextField() {
		component = new JTextField();
		component.setColumns(10);
	}
	
	@Override
	public String getValue() {
		return component.getText();
	}

	@Override
	public void setValue(String value) {
		component.setText(value);
	}

	@Override
	public void setOptions(Void options) {
		// Do nothing
	}

	@Override
	public void setValueAsString(String value) {
		component.setText(value);
	}

	@Override
	public String getValueAsString() {
		return getValue();
	}

	@Override
	public JTextField getComponent() {
		return component;
	}

}
