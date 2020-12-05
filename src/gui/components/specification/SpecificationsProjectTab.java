package gui.components.specification;

import controller.ProjectController;
import gui.components.core.PanelManager;
import gui.components.core.TitleBar;

import javax.swing.*;
import java.awt.*;

public class SpecificationsProjectTab extends JPanel {
	private PanelManager panelManager;
	private String previousId;
	private ProjectController projectController;
	
	public SpecificationsProjectTab(PanelManager panelManager, ProjectController projectController) {
		this.panelManager = panelManager;
		this.projectController = projectController;
		previousId = panelManager.getCurrentId();
		setLayout(new BorderLayout(0, 0));
		
		TitleBar titleBar = new TitleBar();
		titleBar.setTitle("Opret projekt");
		titleBar.setButtonName("Gaa tilbage");
		titleBar.addActionListener(e -> panelManager.setActiveAndRemoveCurrent(previousId));
		add(titleBar, BorderLayout.NORTH);
		
		JPanel container = new JPanel();
		add(container, BorderLayout.CENTER);
		container.setLayout(new BorderLayout(0, 0));
		
		JPanel titleContainer = new JPanel();
		container.add(titleContainer, BorderLayout.NORTH);
		
		JLabel title = new JLabel("Specifikationer (2/3)");
		title.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		titleContainer.add(title);
		
		JComponent specificationsTab = new SpecificationsTab(panelManager);
		container.add(specificationsTab, BorderLayout.CENTER);
	}
}
