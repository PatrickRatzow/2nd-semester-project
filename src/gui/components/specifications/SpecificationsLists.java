package gui.components.specifications;

import controller.SpecificationController;
import entity.Specification;
import gui.components.core.PanelManager;
import gui.components.core.Row;
import gui.components.specifications.specification.SpecificationTab;
import gui.util.Colors;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class SpecificationsLists extends JPanel {
	private JPanel specificationsList;
	private JPanel chosenSpecifications;
	private PanelManager panelManager;
	private Map<Integer, Entry<Specification, SpecificationsSelectedRow>> chosenMap;
	private int displayId = 0;

	public SpecificationsLists(PanelManager panelManager) {
		setOpaque(false);
		this.panelManager = panelManager;
		chosenMap = new HashMap<>();
		setLayout(new MigLayout("insets 0, gap rel 0", "[::250px,grow][grow]", "[][grow]"));

		JPanel listContainer = new JPanel();
		listContainer.setBackground(Colors.PRIMARY.getColor());
		listContainer.setFont(new Font("Tahoma", Font.BOLD, 15));
		add(listContainer, "cell 0 0,grow");

		JLabel titleList = new JLabel("Specifikationer");
		titleList.setFont(new Font("Tahoma", Font.BOLD, 15));
		listContainer.add(titleList);

		JPanel chosenContainer = new JPanel();
		chosenContainer.setBackground(Colors.PRIMARY.getColor());
		chosenContainer.setFont(new Font("Tohoma", Font.BOLD, 15));
		add(chosenContainer, "cell 1 0,grow");

		JLabel titleChosen = new JLabel("Valgte specifikationer");
		titleChosen.setFont(new Font("Tahoma", Font.BOLD, 15));
		chosenContainer.add(titleChosen);

		JScrollPane listScrollPane = new JScrollPane();
		listScrollPane.setOpaque(false);
		add(listScrollPane, "cell 0 1,grow");

		specificationsList = new JPanel();
		listScrollPane.setViewportView(specificationsList);
		specificationsList.setBackground(Colors.SECONDARY.getColor());
		specificationsList.setLayout(new BoxLayout(specificationsList, BoxLayout.Y_AXIS));

		JScrollPane chosenScrollPane = new JScrollPane();
		add(chosenScrollPane, "cell 1 1,grow");

		chosenSpecifications = new JPanel();
		chosenScrollPane.setViewportView(chosenSpecifications);
		chosenSpecifications.setBackground(Colors.SECONDARY.getColor());
		chosenSpecifications.setLayout(new BoxLayout(chosenSpecifications, BoxLayout.Y_AXIS));
	}

	public List<Specification> getSpecifications() {
		return chosenMap.values().stream().map(Entry::getKey).collect(Collectors.toList());
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
		specificationRow.addActionListener(e -> panelManager.setActive("specification_tab", () -> {
			SpecificationController specificationController = new SpecificationController(specification);
			specificationController.setDisplayId(displayId++);
			specificationController.addSaveListener(this::createChosenRow);

			return new SpecificationTab(panelManager, specificationController);
		}));

		return specificationRow;
	}
	
	private String getDisplayName(Specification spec) {
		return spec.getDisplayName() + " (x" + spec.getResultAmount() + ")";
	}

	private void createChosenRow(SpecificationController specificationController) {
		Specification spec = specificationController.getSpecification();
		int displayId = specificationController.getDisplayId();
		Entry<Specification, SpecificationsSelectedRow> entry = chosenMap.get(displayId);
		if (entry != null) {
			entry.getValue().setTitleText(getDisplayName(spec));
			
			chosenMap.replace(displayId, new SimpleEntry<>(spec, entry.getValue()));
		} else {
			boolean even = (chosenMap.size() + 1) % 2 == 0;
			SpecificationsSelectedRow row = new SpecificationsSelectedRow(getDisplayName(spec), even);
			row.addActionListener(e -> {
				panelManager.setActive("specification_tab", () -> {
					specificationController.removeAllSaveListeners();
					specificationController.addSaveListener(this::createChosenRow);
					SpecificationTab tab = new SpecificationTab(panelManager, specificationController);
					tab.fillColumnsWithRequirementValues();

					return tab;
				});
			});
			row.addRemoveButtonActionListener(e -> {
				chosenMap.remove(displayId);
				chosenSpecifications.remove(row);
				chosenSpecifications.revalidate();
				chosenSpecifications.repaint();
			});
			row.setMaximumSize(new Dimension(10000, 50));
			chosenSpecifications.add(row);

			chosenMap.put(displayId, new SimpleEntry<>(spec, row));
		}
	}
}