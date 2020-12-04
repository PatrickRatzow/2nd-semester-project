package gui.components.specification;

import controller.SpecificationsController;
import entity.Specification;
import gui.components.core.PanelManager;
import gui.components.core.Row;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SpecificationsLists extends JPanel {
	private JPanel specificationsList;
	private JPanel chosenSpecifications;
	private PanelManager panelManager;
	private SpecificationsController specificationsController;

	public SpecificationsLists(PanelManager panelManager) {
		this.panelManager = panelManager;
		specificationsController = new SpecificationsController();
		
		setLayout(new MigLayout("", "[::250px,grow][grow]", "[][grow]"));
		
		JPanel listContainer = new JPanel();
		listContainer.setBackground(Color.GRAY);
		listContainer.setFont(new Font("Tahoma", Font.BOLD, 15));
		add(listContainer, "cell 0 0,grow");
		
		JLabel titleList = new JLabel("Specifikationer");
		titleList.setFont(new Font("Tahoma", Font.BOLD, 15));
		listContainer.add(titleList);

		JPanel chosenContainer = new JPanel();
		chosenContainer.setBackground(Color.GRAY);
		chosenContainer.setFont(new Font("Tohoma", Font.BOLD, 15));
		add(chosenContainer, "cell 1 0,grow");
		
		JLabel titleChosen = new JLabel("Valgte specifikationer");
		titleChosen.setFont(new Font("Tahoma", Font.BOLD, 15));
		chosenContainer.add(titleChosen);
	
		JScrollPane listScrollPane = new JScrollPane();
		add(listScrollPane, "cell 0 1,grow");

		specificationsList = new JPanel();
		listScrollPane.setViewportView(specificationsList);
		specificationsList.setBackground(Color.GRAY);
		specificationsList.setLayout(new BoxLayout(specificationsList, BoxLayout.Y_AXIS));

		JScrollPane chosenScrollPane = new JScrollPane();
		add(chosenScrollPane, "cell 1 1,grow");

		chosenSpecifications = new JPanel();
		chosenScrollPane.setViewportView(chosenSpecifications);
		chosenSpecifications.setBackground(Color.GRAY);
		chosenSpecifications.setLayout(new BoxLayout(chosenSpecifications, BoxLayout.Y_AXIS));
		
		loadSpecifications();
	}

	private Row createListRow(Specification specification) {
		Row specificationRow = new Row();
		specificationRow.setTitleText(specification.getName());
		specificationRow.setButtonText("Tilf\u00F8j");
		specificationRow.addActionListener(e ->
			panelManager.setActive("specification_tab", () -> new SpecificationTab(panelManager, specification)));
		specificationRow.setMaximumSize(new Dimension(10000, 50));
		
		return specificationRow;
	}
	
	private Row createSelectedRow(Specification specification) {
		Row specificationRow = new Row();
		specificationRow.setTitleText(specification.getName());
		specificationRow.setButtonText("Rediger");
		specificationRow.addActionListener(System.out::println);
		specificationRow.setMaximumSize(new Dimension(10000, 50));
		
		return specificationRow;
	}
	
	private void loadSpecifications() {
		List<Specification> specifications;
		
		specifications = specificationsController.getSpecifications();
		for (Specification specification : specifications) {
			specificationsList.add(createListRow(specification));
			chosenSpecifications.add(createSelectedRow(specification));
		}
	}
}