package gui.components.specification;

import javax.swing.*;
import java.awt.*;

public class SpecificationColumn extends JPanel {
	private SpecificationColumnValueField<?, ?, ?> valueField;
	private JLabel titleName;
	
	public SpecificationColumn(String titleText, SpecificationColumnValueField<?, ?, ?> valueField) {
		setMaximumSize(new Dimension(400, 50));
		setPreferredSize(new Dimension(457, 65));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
				
		JPanel panel = new JPanel();
		add(panel);
		panel.setOpaque(true);
		panel.setLayout(new BorderLayout(0, 0));
		panel.setMaximumSize(new Dimension(10000, 25));
		
		titleName = new JLabel("New label", SwingConstants.CENTER);
		titleName.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		titleName.setText(titleText);
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
	
	public String getStringValue() {
		return valueField.getValueAsString();
	}
}
