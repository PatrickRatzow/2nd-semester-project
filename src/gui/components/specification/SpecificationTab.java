package gui.components.specification;

import controller.SpecificationController;
import entity.Requirement;
import entity.Specification;
import gui.components.core.PanelManager;
import gui.components.core.TitleBar;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@SuppressWarnings("serial")
public class SpecificationTab extends JPanel {
	private PanelManager panelManager;
	private String previousId;
	private SpecificationController specificationController;
	private SpecificationColumn nameColumn;
	private SpecificationColumn amountColumn;
	private Map<SpecificationColumn, Requirement> columns;

	public SpecificationTab(PanelManager panelManager, SpecificationController specificationController) {
		this.panelManager = panelManager;
		this.specificationController = specificationController;
		previousId = panelManager.getCurrentId();
		List<Requirement> requirements = specificationController.getRequirements();
		columns = new HashMap<>();
		String name = specificationController.getDislayName();

		setLayout(new BorderLayout(0, 0));
		TitleBar title = new TitleBar();
		title.setTitle("Specification");
		title.setButtonName("Gaa Tilbage");
		add(title, BorderLayout.NORTH);
		
		JPanel buttomBar = new JPanel();
		add(buttomBar, BorderLayout.SOUTH);
		buttomBar.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton save = new JButton("Gem krav");
		save.setBackground(Color.GREEN);
		save.setForeground(Color.BLACK);
		save.setHorizontalAlignment(SwingConstants.RIGHT);
		buttomBar.add(save);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new CardLayout());
		
		JPanel widthContainer = new JPanel();
		widthContainer.setMaximumSize(new Dimension(400, 10000));
		panel.add(widthContainer);
		widthContainer.setLayout(new BoxLayout(widthContainer, BoxLayout.Y_AXIS));

		JPanel titleContainer = new JPanel();
		titleContainer.setMaximumSize(new Dimension(10000, 50));
		
		widthContainer.add(titleContainer);
		titleContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JLabel titleLabel = new JLabel(name);
		titleContainer.add(titleLabel);
		titleLabel.setFont(new Font(titleLabel.getFont().toString(), Font.PLAIN, 20));

		
		setUnderlineToFont(titleLabel);
		createSpecificationColumns(widthContainer);		
		loadSpecification(widthContainer, specificationController);
		
		something(requirements, widthContainer); //Needs an other name, just havent thought of that name yet
		
		System.out.println("Added requirements: " + requirements); // delete later
		
		title.addActionListener(e -> {
			String currentId = panelManager.getCurrentId();
			
			panelManager.setActive(previousId);
			panelManager.removePanel(currentId);
		});
		
		//Send reuqirements have been saved, and back to Specifications window. // fulfilled;
		save.addActionListener(e -> {
			String resultNameInTextField = nameColumn.getStringValue();
			String resultAmountInTextField = amountColumn.getStringValue();	
				
			int parseAmount = Integer.parseInt(resultAmountInTextField);
			System.out.println("Krav er gemt");
			specificationController.setDisplayName(resultNameInTextField);
			specificationController.setResultAmount(parseAmount);

			List<Requirement> savedRequirements = new LinkedList<>();
			for (Entry<SpecificationColumn, Requirement> column : columns.entrySet()) {
				String value = column.getKey().getStringValue();
				column.getValue().setValueFromSQLValue(value);
				
				savedRequirements.add(column.getValue());
			}
			specificationController.setRequirements(savedRequirements);
			
			System.out.println(specificationController.getDislayName());
			System.out.println(specificationController.getResultAmount());
			System.out.println(specificationController.getRequirements());
			
			specificationController.save();
			String currentId = panelManager.getCurrentId();
			
			panelManager.setActive(previousId);
			panelManager.removePanel(currentId);
		});
	}
	
	//Check if the fields we are trying to load are not empty, then we will load them all;
	//Else we assume that it has not been created and we cannot set anything;
	public void loadSpecification(JPanel widthContainer, SpecificationController specController) {
		Specification spec = specController.getSpecification();
		System.out.println("WE ARE TRYING TO EDIT " + spec.getDisplayName());
		
		
		for (Entry<SpecificationColumn, Requirement> column : columns.entrySet()) {
			String value = column.getKey().getStringValue();
			Requirement requirement = column.getValue();
			column.getValue().setValueFromSQLValue(value);
			column.setValue(requirement);
		}
		System.out.println(spec.getDisplayName());
	}
	
	private void createSpecificationColumns(JPanel widthContainer) {
		widthContainer.add(createSpacer());
		nameColumn = createSpecificationColumn("Navn");
		widthContainer.add(nameColumn);
		widthContainer.add(createSpacer());
		amountColumn = createSpecificationColumn("Antal");
		widthContainer.add(amountColumn);
		widthContainer.add(createSpacer());
	}
	
	private void something(List<Requirement> requirements, JPanel widthContainer) {
		for (Requirement requirement : requirements) {
			SpecificationColumn specificationColumn = createRequirementColumn(requirement);

			widthContainer.add(specificationColumn);
			widthContainer.add(createSpacer());
			columns.put(specificationColumn, requirement);
		}
	}
	
	private SpecificationColumn createRequirementColumn(Requirement requirement) {
		SpecificationColumnValueField field = SpecificationColumnValueFieldFactory.create(requirement);
		SpecificationColumn rows = new SpecificationColumn(requirement.getName(), field);

		return rows;
	}
	
	private SpecificationColumn createSpecificationColumn(String displayValue) {
		SpecificationColumnValueField field = new SpecificationColumnTextField();
		SpecificationColumn rows = new SpecificationColumn(displayValue, field);

		return rows;
	}
	
	private void setUnderlineToFont(JLabel titleLabel) {
		Font font = titleLabel.getFont();
		Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		titleLabel.setFont(font.deriveFont(attributes));
	}
	
	
	private Component createSpacer() {
		return Box.createRigidArea(new Dimension(0, 20));
	}
}