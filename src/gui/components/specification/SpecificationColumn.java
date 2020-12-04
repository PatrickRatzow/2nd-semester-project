package gui.components.specification;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class SpecificationColumn extends JPanel {
	
	private JTextField textField;
	private JLabel titleName;

	/**
	 * Create the panel.
	 */
	
	public SpecificationColumn() {
		this("unnamed");
	}
	
	public SpecificationColumn(String labelName) {
		setMaximumSize(new Dimension(400, 50));
		setPreferredSize(new Dimension(400, 50));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
				
		JPanel panel = new JPanel();
		add(panel);
		panel.setOpaque(true);
		panel.setLayout(new BorderLayout(0, 0));
		panel.setMaximumSize(new Dimension(10000, 25));
		
		titleName = new JLabel("New label", SwingConstants.CENTER);
		panel.add(titleName, BorderLayout.CENTER);
		setTitleName(labelName);
		
		textField = new JTextField();
		add(textField);
		textField.setColumns(10);

	} 
	
	public SpecificationColumn(String labelName) {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		
		titleName.setFont(new Font(titleName.getFont().toString(), 1, 14));
		
	}
	
	
	public void setTitleName(String name) {
		titleName.setText(name);
	}
	
	public JTextField getTextField() {
		return textField;
	}

}
