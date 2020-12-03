package gui;

import gui.components.SpecificationRow;
import gui.components.Titlebar;

import javax.swing.*;
import java.awt.*;

public class SpecificationTab extends JPanel {

	/**
	 * Create the panel.
	 */
	public SpecificationTab() {
		setLayout(new BorderLayout(0, 0));
		
		Titlebar title = new Titlebar();
		title.setTitle("Specification");
		title.setButtonName("G� Tilbage");
		add(title, BorderLayout.NORTH);
		
		JPanel buttomBar = new JPanel();
		add(buttomBar, BorderLayout.SOUTH);
		buttomBar.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton save = new JButton("Gem krav");
		save.setHorizontalAlignment(SwingConstants.RIGHT);
		buttomBar.add(save);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		for(int i = 0; i < 10; i++) {
			panel.add(createRow(i));
		}
	}
	
	
	private SpecificationRow createRow(int i) {
		SpecificationRow rows = new SpecificationRow();
		rows.setTitleName(String.valueOf(i));
		
		
		return rows;
	}
	
}
