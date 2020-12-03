package gui;

import gui.components.Row;
import gui.components.Titlebar;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Projects extends JPanel {
	private List<Row> projects = new LinkedList<>();
	
	public Projects() {
		setLayout(new BorderLayout(0, 0));
		
		Titlebar titlebar = new Titlebar();
		titlebar.setMinimumSize(new Dimension(184, 50));
		add(titlebar, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new GridLayout(0, 1));
		
		for (int i = 0; i < 10; i++) {
			panel.add(createRow(i));
		}
	}
	
	private Row createRow(int number) {
		Row row = new Row();
		row.setTitleText(String.valueOf(number));
		row.setButtonText("Åben");
		
		return row;
	}

}
