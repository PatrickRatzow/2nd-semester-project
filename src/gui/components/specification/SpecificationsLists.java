package gui.components.specification;

import controller.SpecificationController;
import entity.Specification;
import gui.components.core.PanelManager;
import gui.components.core.Row;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class SpecificationsLists extends JPanel {
	private JPanel specificationsList;
	private JPanel chosenSpecifications;
	private PanelManager panelManager;
	private Color bColor;
	private Map<Integer, ChosenSpecificationRow> chosenMap;
	private int displayId = 0;

	public SpecificationsLists(PanelManager panelManager) {
		this.panelManager = panelManager;
		chosenMap = new HashMap<>();
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
	}

	public List<Specification> getSpecifications() {
		return chosenMap.values().stream()
				.map(ChosenSpecificationRow::getSpecification)
				.collect(Collectors.toList());
	}

	public void setSpecifications(List<Specification> specifications) {
		int size = specifications.size();
		for (int i = 0; i < size; i++) {
			Specification specification = specifications.get(i);
			specificationsList.add(createListRow(specification, (i + 1) % 2 == 0));
		}
	}
	
	private Row createListRow(Specification specification, boolean even) {
		Row specificationRow = new Row(even);
		specificationRow.setTitleText(specification.getName());
		specificationRow.setButtonText("Tilf\u00F8j");
		specificationRow.addActionListener(e ->
			panelManager.setActive("specification_tab", () -> {
				SpecificationController specificationController = new SpecificationController(specification);
				specificationController.setDisplayId(displayId++);
				specificationController.addSaveListener(this::createChosenRow);
				
				return new SpecificationTab(panelManager, specificationController);
			})
		);
		
		return specificationRow;
	}

	private void createChosenRow(SpecificationController specificationController) {
		Specification spec = specificationController.getSpecification();
		int displayId = specificationController.getDisplayId();
		ChosenSpecificationRow existingRow = chosenMap.get(displayId);
		if (existingRow != null) {
			existingRow.setName(spec.getDisplayName());
			existingRow.setSpecification(spec);
		} else {
			boolean even = (chosenMap.size() + 1) % 2 == 0;
			ChosenSpecificationRow row = new ChosenSpecificationRow(spec.getDisplayName(), even);
			row.addActionListener(e -> {
				panelManager.setActive("specification_tab", () -> {
					specificationController.addSaveListener(this::createChosenRow);
					
					return new SpecificationTab(panelManager, specificationController);
				});
			});
			row.setMaximumSize(new Dimension(10000, 50));
			row.setSpecification(spec);
			chosenSpecifications.add(row);

			chosenMap.put(displayId, row);
		}
	}
}