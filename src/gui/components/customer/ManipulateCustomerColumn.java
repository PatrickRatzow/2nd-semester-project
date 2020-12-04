package gui.components.customer;

import javax.swing.*;
import java.awt.*;

public class ManipulateCustomerColumn extends JPanel {
	private JTextField content;
	private JLabel title;
	
	public ManipulateCustomerColumn(String titleText) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		panel.setMaximumSize(new Dimension(32767, 35));
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		title = new JLabel(titleText);
		title.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		panel.add(title, BorderLayout.WEST);
		
		content = new JTextField();
		content.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		add(content);
		content.setColumns(10);
		content.setMaximumSize(new Dimension(32767, 30));
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


