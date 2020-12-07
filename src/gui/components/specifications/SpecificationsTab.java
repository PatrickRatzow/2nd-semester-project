package gui.components.specifications;

import controller.ProjectController;
import controller.SpecificationsController;
import gui.components.core.PanelManager;
import gui.util.Colors;
import model.Product;
import model.Specification;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SpecificationsTab extends JPanel {
    private final PanelManager panelManager;
    private ProjectController projectController;
    private final SpecificationsController specificationsController;

    public SpecificationsTab(PanelManager panelManager) {
        specificationsController = new SpecificationsController();
        setOpaque(false);
        this.panelManager = panelManager;
        setLayout(new BorderLayout(0, 0));

        SpecificationsLists specificationsLists = new SpecificationsLists(panelManager);
        specificationsController.addFindListener(specificationsLists::setSpecifications);
        specificationsController.getSpecifications();
        add(specificationsLists, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout(0, 0));
        add(panel, BorderLayout.SOUTH);

        Component rigidArea = Box.createRigidArea(new Dimension(0, 5));
        panel.add(rigidArea, BorderLayout.NORTH);

        JButton continueBtn = new JButton("G\u00E5 videre");
        continueBtn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        continueBtn.setBackground(Colors.GREEN.getColor());
        continueBtn.addActionListener(l -> {
            List<Specification> specifications = specificationsLists.getSpecifications();

            continueBtn.setEnabled(false);
            specificationsController.setSpecifications(specifications);
            specificationsController.getProductsFromSpecifications();
        });
        panel.add(continueBtn, BorderLayout.EAST);
    }

    public void addSaveListener(Consumer<Map<Specification, List<Product>>> listener) {
        specificationsController.addSaveListener(listener);
    }
}
