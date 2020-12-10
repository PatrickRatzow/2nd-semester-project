package gui.tabs.project;

import controller.ProjectController;
import gui.components.core.PanelManager;
import gui.components.core.TitleBar;
import gui.components.project.ProjectOverview;

import javax.swing.*;
import java.awt.*;

public class ProjectView extends JPanel {
	public ProjectView(PanelManager panelManager, ProjectController projectController) {
		setLayout(new BorderLayout(0, 5));

		TitleBar titleBar = new TitleBar();
		titleBar.setTitle(projectController.getName());
		titleBar.setButtonName("Gå Tilbage");
		titleBar.addActionListener(l -> panelManager.setActiveAndRemoveCurrent(panelManager.getPreviousId()));
		add(titleBar, BorderLayout.NORTH);
		
		ProjectOverview overview = new ProjectOverview(panelManager, projectController);
		add(overview, BorderLayout.CENTER);
	}
}
