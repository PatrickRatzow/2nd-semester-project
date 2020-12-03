package gui;

import gui.components.ProjectRow;
import gui.components.TitleBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Projects extends JPanel {
	public Projects() {
		setLayout(new BorderLayout(0, 0));
		
		TitleBar titleBar = new TitleBar();
		titleBar.createSearchBar();
		titleBar.setMinimumSize(new Dimension(184, 50));
		add(titleBar, BorderLayout.NORTH);
		
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
		row.setButtonText("�ben");
		row.setCompleted(isCompleted);
		row.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Clicked on " + name);
			}
		});
		
	
		return row;
	}

}
