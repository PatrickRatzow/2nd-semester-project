package gui;

import gui.components.ProjectRow;
import gui.components.Titlebar;

import javax.swing.*;
import java.awt.*;

public class Projects extends JPanel {
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
			panel.add(createRow(String.valueOf(i), i % 2 == 0));
		}
	}
	
	private ProjectRow createRow(String name, boolean isCompleted) {
		ProjectRow row = new ProjectRow();
		row.setTitleText(name);
		row.setButtonText("Åben");
		row.setCompleted(isCompleted);
		
		return row;
	}

}
