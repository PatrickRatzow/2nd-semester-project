package gui.components.customer;

import gui.components.core.PlaceholderTextField;

import javax.swing.*;
import java.awt.*;

public class ManipulateCustomerColumn extends JPanel {
	private PlaceholderTextField content;
	private JLabel title;
	
	public ManipulateCustomerColumn(String titleText) {
		setOpaque(false);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setMaximumSize(new Dimension(32767, 35));
		panel.setLayout(new BorderLayout(0, 0));
		add(panel);
		
		title = new JLabel(titleText);
		title.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		panel.add(title, BorderLayout.WEST);
		
		content = new PlaceholderTextField();
		content.setPlaceholder("Placeholder tekst");
		content.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		content.setColumns(10);
		content.setMaximumSize(new Dimension(32767, 30));
		add(content);
	}
	
	public void setTitleText(String text) {
		title.setText(text);
	}
	
	public void setContentText(String text) {
		content.setText(text);
	}
	
	public String getContentText() {
		return content.getText();
	}
}


