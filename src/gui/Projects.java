package gui;

import gui.components.core.TabPanel;
import gui.components.project.ProjectsOverview;

import java.awt.*;

public class Projects extends TabPanel {
	public Projects() {
		setLayout(new CardLayout(0, 0));
		setActive("projects", () -> new ProjectsOverview(this));
	}
}
