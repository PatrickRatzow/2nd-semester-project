package gui.components.specification;

import gui.components.core.PanelManager;

import javax.swing.*;

import controller.ProjectController;

import java.awt.*;

public class SpecificationsTab extends JPanel {
	private PanelManager panelManager;
	private ProjectController projectController;
	
	public SpecificationsTab(PanelManager panelManager) {
		setOpaque(false);
		this.panelManager = panelManager;
		setLayout(new BorderLayout(0, 0));
	
		SpecificationsLists specificationsLists = new SpecificationsLists(panelManager, projectController);
		add(specificationsLists, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		Component rigidArea = Box.createRigidArea(new Dimension(0, 10));
		panel.add(rigidArea, BorderLayout.NORTH);
		
		JButton continueBtn = new JButton("G\u00E5 videre");
		continueBtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		continueBtn.setForeground(new Color(0, 0, 0));
		continueBtn.setBackground(new Color(152, 251, 152));
		panel.add(continueBtn, BorderLayout.EAST);
	}
}
