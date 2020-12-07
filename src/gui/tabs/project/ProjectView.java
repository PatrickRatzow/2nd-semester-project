package gui.tabs.project;

import controller.ProjectController;
import gui.components.core.PanelManager;
import gui.components.core.TitleBar;
import gui.components.project.ProjectOverview;
import model.Project;

import javax.swing.*;
import java.awt.*;

public class ProjectView extends JPanel {
	public ProjectView(PanelManager panelManager, Project project) {
		String previousId = panelManager.getCurrentId();
		setLayout(new BorderLayout(0, 0));

		TitleBar titleBar = new TitleBar();
		titleBar.setTitle(project.getName());
		titleBar.setButtonName("Gå Tilbage");
		titleBar.addActionListener(l -> panelManager.setActiveAndRemoveCurrent(previousId));
		add(titleBar, BorderLayout.NORTH);
		
		ProjectController projectController = new ProjectController(project);
		ProjectOverview overview = new ProjectOverview(panelManager, projectController);
		add(overview, BorderLayout.CENTER);
	}
}
