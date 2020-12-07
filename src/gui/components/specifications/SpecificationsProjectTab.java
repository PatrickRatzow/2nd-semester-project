package gui.components.specifications;

import controller.ProjectController;
import gui.components.core.BackgroundTitle;
import gui.components.core.PanelManager;
import gui.components.core.TitleBar;

import javax.swing.*;
import java.awt.*;

public class SpecificationsProjectTab extends JPanel {
    private final PanelManager panelManager;
    private final String previousId;
    private final ProjectController projectController;

    public SpecificationsProjectTab(PanelManager panelManager, ProjectController projectController) {
        this.panelManager = panelManager;
        this.projectController = projectController;
        previousId = panelManager.getCurrentId();
        setLayout(new BorderLayout(0, 0));

        TitleBar titleBar = new TitleBar();
        titleBar.setTitle("Opret projekt");
        titleBar.setButtonName("Gaa tilbage");
        titleBar.addActionListener(e -> panelManager.setActiveAndRemoveCurrent(previousId));
        add(titleBar, BorderLayout.NORTH);

        JPanel container = new JPanel();
        add(container, BorderLayout.CENTER);
        container.setLayout(new BorderLayout(0, 0));

        BackgroundTitle backgroundContainer = new BackgroundTitle();
        backgroundContainer.setTitle("Specifikationer (2/3)");
        container.add(backgroundContainer);

        JComponent specificationsTab = new SpecificationsTab(panelManager);
        backgroundContainer.add(specificationsTab);
    }
}
