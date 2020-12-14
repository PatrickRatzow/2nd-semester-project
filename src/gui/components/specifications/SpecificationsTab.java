package gui.components.specifications;

import controller.OrderController;
import controller.SpecificationController;
import controller.SpecificationsController;
import gui.Frame;
import gui.components.core.PanelManager;
import util.Colors;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.function.Consumer;

public class SpecificationsTab extends JPanel {
    private final PanelManager panelManager;
    private final SpecificationsController specificationsController;
    private final JButton continueBtn;

    public SpecificationsTab(PanelManager panelManager) {
        specificationsController = new SpecificationsController();
        this.panelManager = panelManager;
        setOpaque(false);
        setLayout(new BorderLayout(0, 0));
        
        createSpecificationsLists();

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout(0, 0));
        add(panel, BorderLayout.SOUTH);

        Component rigidArea = Box.createRigidArea(new Dimension(0, 5));
        panel.add(rigidArea, BorderLayout.NORTH);

        continueBtn = new JButton("Gå videre");
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
        SpecificationsLists specificationsLists = new SpecificationsLists(panelManager, specificationsController);
        add(specificationsLists, BorderLayout.CENTER);
        specificationsController.addFindListener(specificationsLists::setSpecifications);
        specificationsController.getSpecifications();
    }
    
    private void continueToNextTab() {
        Collection<SpecificationController> specifications = specificationsController.getSpecificationControllers();
        if (specifications.isEmpty()) {
            createError(new Exception("Tilføj mindst en specifikation!"));
            
            return;
        }
        
        continueBtn.setEnabled(false);
        specificationsController.getProductsFromSpecifications();
    }
    
    public void addSaveListener(Consumer<OrderController> listener) {
        specificationsController.addSaveListener(listener);
    }
}
