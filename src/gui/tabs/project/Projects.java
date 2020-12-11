package gui.tabs.project;

import controller.ProjectController;
import gui.components.core.PanelManager;
import gui.components.core.TitleBar;
import gui.components.project.ProjectRow;
import gui.tabs.Tab;
import model.Project;
import model.ProjectStatus;

import javax.swing.*;
import java.awt.*;

public class Projects extends Tab {
    private final ProjectController projectController;
    private final JPanel panel;

    public Projects(PanelManager panelManager) {
        super(panelManager);
        
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
        titleBar.addActionListener(e ->
            panelManager.setActive("find_project_customer", () ->
                    new ProjectFindOrCreateCustomer(panelManager, projectController)));

        JScrollPane scrollPane = new JScrollPane();
        add(scrollPane, BorderLayout.CENTER);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        scrollPane.setViewportView(panel);
        
        projectController.addFindListener(projects -> {
            panel.removeAll();
            for (Project project : projects) {
                createRow(project);
            }
            revalidate();
            repaint();
        });
        projectController.addFindProjectListener(project ->
            panelManager.setActive("project_overview", () ->
                    new ProjectView(panelManager, new ProjectController(project))
            )
        );
        projectController.getAll();
    }
    
    private void createRow(Project project) {
        boolean even = (panel.getComponents().length + 1) % 2 == 0;

        ProjectRow row = new ProjectRow(even);
        row.setTitleText(project.getName());
        row.setButtonText("Aaben");
        row.setCompleted(project.getStatus().equals(ProjectStatus.FINISHED));
        row.addActionListener(e -> projectController.getFullProject(project));
        panel.add(row);
    }
}
