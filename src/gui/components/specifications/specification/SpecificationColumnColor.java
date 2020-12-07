package gui.components.specifications.specification;

import javax.swing.*;
import java.util.Collection;

public class SpecificationColumnColor implements SpecificationColumnValueField<String, JComboBox<String>, Collection<String>> {
    private final JComboBox<String> component;

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
    public void setValueAsString(String value) {
        int found = -1;

        int size = component.getItemCount();
        for (int i = 0; i < size && found == -1; i++) {
            String option = component.getItemAt(i);
            if (option.equals(value)) {
                found = i;
            }
        }

        if (found != -1) {
            component.setSelectedIndex(found);
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
