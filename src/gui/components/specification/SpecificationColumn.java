package gui.components.specification;

import javax.swing.*;
import java.awt.*;

public class SpecificationColumn extends JPanel {
	
	private JTextField textField;
	private JLabel titleName;

	/**
	 * Create the panel.
	 */
	public SpecificationColumn() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 10));
		
		titleName = new JLabel("New label", SwingConstants.CENTER);
		panel_1.add(titleName);
		
		textField = new JTextField();
		panel_1.add(textField);
		textField.setColumns(10);
	} 
	
	public SpecificationColumn(String labelName) {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 10));
		
		titleName = new JLabel("New label", SwingConstants.CENTER);
		panel_1.add(titleName);
		setTitleName(labelName);
	}
	
	
	public void setTitleName(String name) {
		titleName.setText(name);
	}
	
	public JTextField getTextField() {
		return textField;
	}

}
