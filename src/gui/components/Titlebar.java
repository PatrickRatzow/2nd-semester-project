package gui.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Titlebar extends JPanel {
	private JLabel title;
	private JButton actionButton;

	public Titlebar() {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBackground(Color.LIGHT_GRAY);
		setLayout(new CardLayout(0, 0));
		
		JPanel container = new JPanel();
		container.setBackground(Color.LIGHT_GRAY);
		container.setOpaque(true);
		container.setBorder(new EmptyBorder(5, 5, 5, 5));
		container.setLayout(new BorderLayout(0, 0));
		add(container, "name_1155987637736200");
		
		title = new JLabel("Insert Title");
		title.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		container.add(title, BorderLayout.WEST);
		
		actionButton = new JButton("Button");
		actionButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		actionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		container.add(actionButton, BorderLayout.EAST);
	}

	public void setTitle(String titleName) {
		title.setText(titleName);
	}

	public void setButtonName(String buttonName) {
		actionButton.setText(buttonName);
	}
}
