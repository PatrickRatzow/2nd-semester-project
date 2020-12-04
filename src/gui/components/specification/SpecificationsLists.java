package gui.components.specification;

import controller.ProjectController;
import controller.SpecificationController;
import controller.SpecificationsController;
import entity.Specification;
import gui.components.core.PanelManager;
import gui.components.core.Row;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SpecificationsLists extends JPanel {
	private JPanel specificationsList;
	private JPanel chosenSpecifications;
	private PanelManager panelManager;
	private SpecificationsController specificationsController;
	private Color bColor;
	private Map<Specification, ChosenSpecificationRow> chosenMap;
	
	public SpecificationsLists(PanelManager panelManager, ProjectController projectController) {
		this.panelManager = panelManager;
		chosenMap = new HashMap<>();
		specificationsController = new SpecificationsController(projectController);
		bColor = new Color(220, 220, 220);
		
		setLayout(new MigLayout("insets 0", "[::250px,grow][grow]", "[][grow]"));
		
		JPanel listContainer = new JPanel();
		listContainer.setBackground(bColor);
		listContainer.setFont(new Font("Tahoma", Font.BOLD, 15));
		add(listContainer, "cell 0 0,grow");
		
		JLabel titleList = new JLabel("Specifikationer");
		titleList.setFont(new Font("Tahoma", Font.BOLD, 15));
		listContainer.add(titleList);

		JPanel chosenContainer = new JPanel();
		chosenContainer.setBackground(bColor);
		chosenContainer.setFont(new Font("Tohoma", Font.BOLD, 15));
		add(chosenContainer, "cell 1 0,grow");
		
		JLabel titleChosen = new JLabel("Valgte specifikationer");
		titleChosen.setFont(new Font("Tahoma", Font.BOLD, 15));
		chosenContainer.add(titleChosen);
	
		JScrollPane listScrollPane = new JScrollPane();
		add(listScrollPane, "cell 0 1,grow");

		specificationsList = new JPanel();
		listScrollPane.setViewportView(specificationsList);
		specificationsList.setOpaque(true);
		specificationsList.setLayout(new BoxLayout(specificationsList, BoxLayout.Y_AXIS));

		JScrollPane chosenScrollPane = new JScrollPane();
		add(chosenScrollPane, "cell 1 1,grow");

		chosenSpecifications = new JPanel();
		chosenScrollPane.setViewportView(chosenSpecifications);
		chosenSpecifications.setOpaque(true);
		chosenSpecifications.setLayout(new BoxLayout(chosenSpecifications, BoxLayout.Y_AXIS));
		
		specificationsController.addFindListener(specifications -> {
			int size = specifications.size();
			for (int i = 0; i < size; i++) {
				Specification specification = specifications.get(i);
				specificationsList.add(createListRow(specification, (i + 1) % 2 == 0));
			}
		});
		specificationsController.addSaveListener(products -> {
			// Code for when we have calculated our products
		});
		
		specificationsController.getSpecifications();
	}
	
	private Row createListRow(Specification specification, boolean even) {
		Row specificationRow = new Row(even);
		specificationRow.setTitleText(specification.getName());
		specificationRow.setButtonText("Tilf\u00F8j");
		specificationRow.addActionListener(e ->
			panelManager.setActive("specification_tab", () -> {
				SpecificationController specificationController = new SpecificationController(specification);
				specificationController.addSaveListener(this::createChosenRow);
				
				return new SpecificationTab(panelManager, specificationController);
			}));
		specificationRow.setMaximumSize(new Dimension(10000, 50));
		
		return specificationRow;
	}
	
	
	private void createChosenRow(Specification spec) {
		ChosenSpecificationRow existingRow = chosenMap.get(spec);
		if (existingRow == null) {
			existingRow.setName(spec.getDisplayName());
		} else {
			boolean even = (chosenMap.size() + 1) % 2 == 0;
			ChosenSpecificationRow row = new ChosenSpecificationRow(spec.getDisplayName(), even);
			row.addActionListener(System.out::println);
			row.setMaximumSize(new Dimension(10000, 50));
			chosenSpecifications.add(row);

			chosenMap.put(spec, row);
		}
	}
}