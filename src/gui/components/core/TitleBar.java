package gui.components.core;

import gui.util.Colors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class TitleBar extends JPanel {
	private JLabel title;
	private JButton actionButton;
	protected JPanel container;
	private PlaceholderTextField searchTextField;
	private JPanel searchContainer;
	
	public TitleBar() {
		setBackground(Colors.PRIMARY.getColor());
		setLayout(new CardLayout(0, 0));
		setPreferredSize(new Dimension(10000, 47));

		container = new JPanel();
		container.setOpaque(false);
		container.setBorder(new EmptyBorder(5, 5, 5, 5));
		container.setLayout(new BorderLayout(0, 0));
		add(container, "name_1155987637736200");
		
		title = new JLabel("Insert Title");
		title.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		container.add(title, BorderLayout.WEST);
		
		actionButton = new JButton("Button");
		actionButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		container.add(actionButton, BorderLayout.EAST);
	}

	public void setTitle(String titleName) {
		title.setText(titleName);
	}

	public void setButtonName(String buttonName) {
		actionButton.setText(buttonName);
	}
	
	public void addActionListener(ActionListener listener) {
		actionButton.addActionListener(listener);
	}

	public void hideButton() {
		actionButton.setVisible(false);
	}
	
	public JTextField createSearchBar(String placeholderText) {
		if (searchContainer != null) return searchTextField;
		
		searchContainer = new JPanel();
		searchContainer.setOpaque(false);
		container.add(searchContainer, BorderLayout.CENTER);
		searchContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		searchTextField = new PlaceholderTextField();
		searchTextField.setPlaceholder(placeholderText);
		searchTextField.setPreferredSize(new Dimension(6, 37));
		searchContainer.add(searchTextField);
		searchTextField.setColumns(20);
		
		return searchTextField;
	}
}
