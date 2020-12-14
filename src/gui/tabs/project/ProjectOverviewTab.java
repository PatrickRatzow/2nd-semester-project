package gui.tabs.project;

import controller.ProjectController;
import gui.components.core.BackgroundTitle;
import gui.components.core.PanelManager;
import gui.components.core.TitleBar;
import gui.components.project.ProjectOverview;

import java.awt.*;

public class ProjectOverviewTab extends BackgroundTitle {
	public ProjectOverviewTab(PanelManager panelManager, ProjectController projectController) {
		String previousId = panelManager.getCurrentId();
		
		setTitle("Oversigt (3/3)");
		setLayout(new BorderLayout(0, 5));
		
		TitleBar titleBar = new TitleBar();
		titleBar.setTitle("Opret Projekt");
		titleBar.setButtonName("Gå Tilbage");
		titleBar.addActionListener(l -> panelManager.setActiveAndRemoveCurrent(previousId));
		add(titleBar, BorderLayout.NORTH);

		ProjectOverview overview = new ProjectOverview(panelManager, projectController);
		add(overview, BorderLayout.CENTER);
	}
}
