package gui.components.project;

import controller.ProjectController;
import controller.SpecificationController;
import entity.Project;
import entity.ProjectStatus;
import entity.Specification;
import entity.specifications.Window;
import gui.components.core.PanelManager;
import gui.components.core.TitleBar;
import gui.components.customer.FindOrCreateCustomer;
import gui.components.specification.SpecificationTab;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ProjectsOverview extends JPanel {
	private ProjectController projectController;
	private JPanel panel;
	private PanelManager panelManager;
	private Map<Project, ProjectRow> rows;

	public ProjectsOverview(PanelManager panelManager) {
		rows = new HashMap<>();
		this.panelManager = panelManager;
		projectController = new ProjectController();

		setLayout(new BorderLayout(0, 0));

		TitleBar titleBar = new TitleBar();
		titleBar.setTitle("Projekter");
		titleBar.setButtonName("Opret nyt projekt");
		JTextField searchBar = titleBar.createSearchBar("Soeg efter projekter");
		searchBar.addActionListener(e -> {
			String text = searchBar.getText();
			if (text.isEmpty()) {
				projectController.getAll();
			} else {
				projectController.getSearchByName(text);
			}
		});
		titleBar.setMinimumSize(new Dimension(184, 50));
		add(titleBar, BorderLayout.NORTH);
		titleBar.addActionListener(e -> {
			panelManager.setActive("find_project_customer", () -> new FindOrCreateCustomer(panelManager, projectController));
		});

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		projectController.addFindListener(projects -> {
			for (Project project : projects) {
				ProjectRow row = rows.get(project);
				if (row == null) {
					createRow(project);
				} else {
					updateRow(row, project);
				}
			}
		});
		
		projectController.getAll();
	}

	private void updateRow(ProjectRow row, Project project) {
		row.setTitleText(project.getName());
		row.setCompleted(project.getStatus().equals(ProjectStatus.FINISHED));
		
		rows.put(project, row);
	}
	
	private void createRow(Project project) {
		boolean even = (rows.size() + 1) % 2 == 0;
		
		ProjectRow row = new ProjectRow(even);
		row.setTitleText(project.getName());
		row.setButtonText("Aaben");
		row.setCompleted(project.getStatus().equals(ProjectStatus.FINISHED));
		row.addActionListener(e -> {
			panelManager.setActive("project_overview", () -> {
				Specification xd = new Window();
				JComponent component = new SpecificationTab(panelManager, new SpecificationController(xd));
				
				return component;
			});
		});
		panel.add(row);

		rows.put(project, row);
	}
}
