package gui.components.project;

import controller.ProjectController;
import entity.Project;
import entity.ProjectStatus;
import entity.Specification;
import entity.specifications.Window;
import exception.DataAccessException;
import gui.ProductFinder;
import gui.components.core.TabPanel;
import gui.components.core.TitleBar;
import gui.components.specification.SpecificationTab;
import gui.components.specification.SpecificationsProjectTab;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProjectsOverview extends JPanel {
	private ProjectController projectController;
	private JPanel panel;
	private TabPanel panelManager;

	public ProjectsOverview(TabPanel panelManager) {
		this.panelManager = panelManager;
		projectController = new ProjectController();

		setLayout(new BorderLayout(0, 0));

		TitleBar titleBar = new TitleBar();
		titleBar.setTitle("Projekter");
		titleBar.setButtonName("Opret nyt projekt");
		JTextField searchBar = titleBar.createSearchBar("S�g efter projekter");
		searchBar.addActionListener(e -> searchProjects(searchBar.getText()));
		titleBar.setMinimumSize(new Dimension(184, 50));
		add(titleBar, BorderLayout.NORTH);
		titleBar.addActionListener(e -> {
			panelManager.setActive("find_project_customer", () -> new SpecificationsProjectTab(panelManager));
		});

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		loadProjects();
	}

	private ProjectRow createRow(Project project) {
		ProjectRow row = new ProjectRow();
		row.setTitleText(project.getName());
		row.setButtonText("Aaben");
		row.setCompleted(project.getStatus().equals(ProjectStatus.FINISHED));
		row.addActionListener(e -> {
			panelManager.setActive("project_overview", () -> {
				Specification xd = new Window();
				JComponent component = new SpecificationTab(panelManager, xd);
				
				return component;
			});
		});

		return row;
	}

	private void loadProjects() {
		new Thread(() -> {
			List<Project> projects;
			try {
				projects = projectController.findAll();
				if (panel != null) {
					panel.removeAll();
					for (Project project : projects) {
						panel.add(createRow(project));
					}
					panel.repaint();
				}
			} catch (DataAccessException e) {
				e.printStackTrace();
			}
		}).start();
	}

	private void searchProjects(String name) {
		if (name.isEmpty()) {
			loadProjects();

			return;
		}

		new Thread(() -> {
			List<Project> projects;
			try {
				projects = projectController.findByName(name, false);
				if (panel != null) {
					panel.removeAll();
					for (Project project : projects) {
						panel.add(createRow(project));
					}
					panel.repaint();
				}
			} catch (DataAccessException e) {
				e.printStackTrace();
			}
		}).start();
	}

}
