package gui;

import gui.components.core.TabPanel;
import gui.components.project.ProjectsOverview;

public class Projects extends TabPanel {
	public Projects() {
		setActive("projects", () -> new ProjectsOverview(this));
	}
}
