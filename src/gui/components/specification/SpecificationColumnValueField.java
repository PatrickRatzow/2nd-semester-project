package gui.components.specification;

import javax.swing.*;

public interface SpecificationColumnValueField<T, Y extends JComponent, Z> {
	T getValue();
	void setValue(T value);
	void setOptions(Z options);
	String getValueAsString();
	Y getComponent();
}
