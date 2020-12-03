package gui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import gui.components.Titlebar;

public class SpecificationTab extends JPanel {
	private JTextField textField_Name;
	private JTextField textField_1;

	/**
	 * Create the panel.
	 */
	public SpecificationTab() {
		setLayout(new BorderLayout(0, 0));
		
		Titlebar title = new Titlebar();
		title.setTitle("Specification");
		title.setButtonName("Gå Tilbage");
		add(title, BorderLayout.NORTH);
		
		JPanel Buttom_bar = new JPanel();
		add(Buttom_bar, BorderLayout.SOUTH);
		Buttom_bar.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton save = new JButton("Gem krav");
		save.setHorizontalAlignment(SwingConstants.RIGHT);
		Buttom_bar.add(save);
		
		JPanel Center_Bar = new JPanel();
		FlowLayout flowLayout = (FlowLayout) Center_Bar.getLayout();
		add(Center_Bar, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		Center_Bar.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel window = new JLabel("Vindue", SwingConstants.CENTER);
		panel.add(window);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblNewLabel = new JLabel("New label", SwingConstants.CENTER);
		panel_1.add(lblNewLabel);
		
		textField_Name = new JTextField();
		panel_1.add(textField_Name);
		textField_Name.setColumns(10);
		
		textField_1 = new JTextField();
		panel_1.add(textField_1);
		textField_1.setColumns(10);

	}
	
	
	public void setRow() {
		
	}
	
}
