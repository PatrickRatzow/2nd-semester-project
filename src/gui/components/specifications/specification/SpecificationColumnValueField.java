package gui.components.specifications.specification;

import javax.swing.*;

public interface SpecificationColumnValueField<T, Y extends JComponent, Z> {
	T getValue();
	void setValue(T value);
	void setOptions(Z options);
	void setValueAsString(String value);
	String getValueAsString();
	Y getComponent();
}
