package gui.tabs.project;

import controller.ProjectController;
import gui.components.core.BackgroundTitle;
import gui.components.core.PanelManager;
import gui.components.core.TitleBar;
import gui.components.specifications.SpecificationsTab;

import javax.swing.*;
import java.awt.*;

public class ProjectSpecificationsTab extends JPanel {
    public ProjectSpecificationsTab(PanelManager panelManager, ProjectController projectController) {
        String previousId = panelManager.getCurrentId();
        setLayout(new BorderLayout(0, 0));

        TitleBar titleBar = new TitleBar();
        titleBar.setTitle("Opret Projekt");
        titleBar.setButtonName("Gå tilbage");
        titleBar.addActionListener(e -> panelManager.setActiveAndRemoveCurrent(previousId));
        add(titleBar, BorderLayout.NORTH);

        JPanel container = new JPanel();
        add(container, BorderLayout.CENTER);
        container.setLayout(new BorderLayout(0, 0));

        BackgroundTitle backgroundContainer = new BackgroundTitle();
        backgroundContainer.setTitle("Specifikationer (2/3)");
        container.add(backgroundContainer);

        SpecificationsTab specificationsTab = new SpecificationsTab(panelManager);
        specificationsTab.addSaveListener(orderController -> {
        	projectController.setOrderController(orderController);
        	
        	panelManager.setActive("project_overview", () -> new ProjectOverviewTab(panelManager, projectController));
        });
        backgroundContainer.add(specificationsTab);
    }
}
