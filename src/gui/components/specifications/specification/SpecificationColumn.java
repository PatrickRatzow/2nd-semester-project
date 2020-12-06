package gui.components.specifications.specification;

import javax.swing.*;
import java.awt.*;

public class SpecificationColumn extends JPanel {
	private SpecificationColumnValueField<?, ?, ?> valueField;
	private JLabel titleName;
	
	public SpecificationColumn(String titleText, SpecificationColumnValueField<?, ?, ?> valueField) {
		setOpaque(false);
		setMaximumSize(new Dimension(400, 50));
		setPreferredSize(new Dimension(457, 65));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
				
		JPanel panel = new JPanel();
		add(panel);
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout(0, 0));
		panel.setMaximumSize(new Dimension(10000, 25));
		
		titleName = new JLabel(titleText, SwingConstants.CENTER);
		titleName.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		panel.add(titleName, BorderLayout.NORTH);
		
		this.valueField = valueField;
		add(this.valueField.getComponent());
	} 
	
	public void setTitleName(String name) {
		titleName.setText(name);
	}
	
	public JLabel getTitleName() {
		return titleName;
	}

	public void setStringValue(String value) {
		valueField.setValueAsString(value);
	}

	public String getStringValue() {
		return valueField.getValueAsString();
	}
}
