package gui;

import gui.components.SpecificationRow;
import gui.components.TitleBar;

import javax.swing.*;
import java.awt.*;

public class SpecificationTab extends JPanel {

	/**
	 * Create the panel.
	 */
	
	private String[] titleNameList = {"Navn", "Farve", "m2", "Antal"};
 	
	public SpecificationTab(int amountOfRows) {
		setLayout(new BorderLayout(0, 0));
		
		TitleBar title = new TitleBar();
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
		
		//change to names
		for(int i = 0; i < titleNameList.length; i++) {
			String getI = titleNameList[i];
			panel.add(createRow(getI));
		}
	}
	
	
	private SpecificationRow createRow(String s) {
		SpecificationRow rows = new SpecificationRow();
		rows.setTitleName(s);
		
		
		return rows;
	}
	
}
