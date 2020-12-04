package gui;

import gui.components.core.PanelManager;
import gui.components.project.ProjectsOverview;

public class Projects extends PanelManager {
	public Projects() {
		setActive("projects", () -> new ProjectsOverview(this));
	}
}
