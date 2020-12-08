package gui.components.specifications;

import controller.OrderController;
import controller.SpecificationsController;
import gui.Frame;
import gui.components.core.PanelManager;
import gui.util.Colors;
import model.Specification;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class SpecificationsTab extends JPanel {
    private final PanelManager panelManager;
    private final SpecificationsController specificationsController;
    private final JButton continueBtn;
    private SpecificationsLists specificationsLists;

    public SpecificationsTab(PanelManager panelManager) {
        specificationsController = new SpecificationsController();
        this.panelManager = panelManager;
        setOpaque(false);
        setLayout(new BorderLayout(0, 0));
        
        createSpecificationsLists();
        
        add(specificationsLists, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout(0, 0));
        add(panel, BorderLayout.SOUTH);

        Component rigidArea = Box.createRigidArea(new Dimension(0, 5));
        panel.add(rigidArea, BorderLayout.NORTH);

        continueBtn = new JButton("G\u00E5 videre");
        continueBtn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        continueBtn.setBackground(Colors.GREEN.getColor());
        continueBtn.addActionListener(l -> continueToNextTab());
        panel.add(continueBtn, BorderLayout.EAST);
    
        specificationsController.addSaveListener(ol -> continueBtn.setEnabled(true));
        specificationsController.addErrorListener(this::createError);
    }
    
    private void createError(Exception error) {
        continueBtn.setEnabled(true);

        Frame.createErrorPopup(error);
    }

    private void createSpecificationsLists() {
        specificationsLists = new SpecificationsLists(panelManager);
        specificationsController.addFindListener(specificationsLists::setSpecifications);
        specificationsController.getSpecifications();
    }
    
    private void continueToNextTab() {
        List<Specification> specifications = specificationsLists.getSpecifications();
        if (specifications.isEmpty()) {
            createError(new Exception("Tilføj mindst en specifikation!"));
            
            return;
        }
        
        continueBtn.setEnabled(false);
        specificationsController.setSpecifications(specifications);
        specificationsController.getProductsFromSpecifications();
    }
    
    public void addSaveListener(Consumer<OrderController> listener) {
        specificationsController.addSaveListener(listener);
    }
}
