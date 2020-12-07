package gui.tabs.project;

import controller.ProjectController;
import controller.SpecificationController;
import gui.components.core.PanelManager;
import gui.components.core.TitleBar;
import gui.components.project.ProjectRow;
import gui.components.specifications.specification.SpecificationTab;
import gui.tabs.Tab;
import model.Project;
import model.ProjectStatus;
import model.Specification;
import model.specifications.Window;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Projects extends Tab {
    private final ProjectController projectController;
    private final JPanel panel;
    private final Map<Project, ProjectRow> rows;

    public Projects(PanelManager panelManager) {
        super(panelManager);

        rows = new HashMap<>();
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
            panelManager.setActive("find_project_customer", () -> new ProjectFindOrCreateCustomer(panelManager, projectController));
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

                return new SpecificationTab(panelManager, new SpecificationController(xd));
            });
        });
        panel.add(row);

        rows.put(project, row);
    }
}
