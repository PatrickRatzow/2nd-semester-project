package gui.components.core;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class Row extends JPanel {
	protected JLabel title;
	protected JButton mainButton;
	protected JPanel rightContainer;
	protected JPanel leftContainer;
	
	public Row() {
		this("Unnamed Row", "Button");
	}
	public Row(String titleText, String buttonText) {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(new BorderLayout(0, 0));
		setMaximumSize(new Dimension(32746, 50));
		
		rightContainer = new JPanel();
		rightContainer.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(rightContainer, BorderLayout.EAST);
		rightContainer.setLayout(new BorderLayout(0, 0));
		
		mainButton = new JButton("New button");
		mainButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		rightContainer.add(mainButton);
		
		leftContainer = new JPanel();
		add(leftContainer, BorderLayout.WEST);
		leftContainer.setLayout(new BorderLayout(0, 0));
		
		title = new JLabel("New label");
		title.setInheritsPopupMenu(false);
		title.setBorder(new EmptyBorder(5, 5, 5, 5));
		title.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		leftContainer.add(title);
	}

	public void setTitleText(String text) {
		title.setText(text);
	}
	
	public void setButtonText(String text) {
		mainButton.setText(text);
	}
	
	public void addActionListener(ActionListener listener) {
		mainButton.addActionListener(listener);
	}
}
