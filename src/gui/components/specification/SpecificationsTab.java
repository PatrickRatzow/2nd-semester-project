package gui.components.specification;

import controller.ProjectController;
import controller.SpecificationsController;
import entity.Product;
import entity.Specification;
import gui.components.core.PanelManager;
import gui.util.Colors;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SpecificationsTab extends JPanel {
	private PanelManager panelManager;
	private ProjectController projectController;
	private SpecificationsController specificationsController;
	
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
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		Component rigidArea = Box.createRigidArea(new Dimension(0, 10));
		panel.add(rigidArea, BorderLayout.NORTH);
		
		JButton continueBtn = new JButton("G\u00E5 videre");
		continueBtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
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
