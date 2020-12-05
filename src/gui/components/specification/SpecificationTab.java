package gui.components.specification;

import controller.SpecificationController;
import entity.Requirement;
import gui.components.core.PanelManager;
import gui.components.core.TitleBar;
import gui.util.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.List;
import java.util.*;
import java.util.Map.Entry;

@SuppressWarnings("serial")
public class SpecificationTab extends JPanel {
	private PanelManager panelManager;
	private String previousId;
	private SpecificationController specificationController;
	private SpecificationColumn nameColumn;
	private SpecificationColumn amountColumn;
	private JPanel widthContainer;

	public SpecificationTab(PanelManager panelManager, SpecificationController specificationController) {
		this.panelManager = panelManager;
		this.specificationController = specificationController;
		previousId = panelManager.getCurrentId();
		List<Requirement> requirements = specificationController.getRequirements();
		Map<SpecificationColumn, Requirement> columns = new HashMap<>();
		String name = specificationController.getDislayName();

		setLayout(new BorderLayout(0, 0));
		TitleBar title = new TitleBar();
		title.setTitle("Specification");
		title.setButtonName("Gaa Tilbage");
		title.addActionListener(e -> {
			String currentId = panelManager.getCurrentId();
			
			panelManager.setActive(previousId);
			panelManager.removePanel(currentId);
		});
		add(title, BorderLayout.NORTH);
		
		JPanel buttomBar = new JPanel();
		add(buttomBar, BorderLayout.SOUTH);
		buttomBar.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton save = new JButton("Gem krav");
		save.setBackground(Colors.GREEN.getColor());
		save.setHorizontalAlignment(SwingConstants.RIGHT);
		save.addActionListener(e -> {
			// Get the values of the fields that we know are there
			String resultNameInTextField = nameColumn.getStringValue();
			String resultAmountInTextField = amountColumn.getStringValue();	
			int parseAmount = Integer.parseInt(resultAmountInTextField);
			// Set the values on our controller
			specificationController.setDisplayName(resultNameInTextField);
			specificationController.setResultAmount(parseAmount);
			// Set the value of our requirement from their respective field
			for (Entry<SpecificationColumn, Requirement> column : columns.entrySet()) {
				String value = column.getKey().getStringValue();
				column.getValue().setValueFromSQLValue(value);
			}
			// Set the requirements and save
			specificationController.setRequirements(new LinkedList<>(columns.values()));
			specificationController.save();
			
			// Go back to the previous panel
			panelManager.setActiveAndRemoveCurrent(previousId);
		});
		buttomBar.add(save);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new CardLayout());
		
		widthContainer = new JPanel();
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
		
		Font font = titleLabel.getFont();
		Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		titleLabel.setFont(font.deriveFont(attributes));

		// Creates name + amount fields
		createSpecificationColumns();	
		
		// Creates the dynamic fields from the requirement
		Iterator<Requirement> iterator = requirements.iterator();
		while (iterator.hasNext()) {
			Requirement requirement = iterator.next();
			SpecificationColumn specificationColumn = createRequirementColumn(requirement);
			widthContainer.add(specificationColumn);
			columns.put(specificationColumn, requirement);
			// Add a spacer if this isn't the last iteration
			if (iterator.hasNext()) {
				widthContainer.add(createSpacer());
			}
		}
	}
	
	private Component createSpacer() {
		return Box.createRigidArea(new Dimension(0, 20));
	}
	
	private void createSpecificationColumns() {
		widthContainer.add(createSpacer());
		nameColumn = createSpecificationColumn("Navn");
		widthContainer.add(nameColumn);
		widthContainer.add(createSpacer());
		amountColumn = createSpecificationColumn("Antal");
		widthContainer.add(amountColumn);
		widthContainer.add(createSpacer());
	}
	
	private SpecificationColumn createRequirementColumn(Requirement<?> requirement) {
		SpecificationColumnValueField<?, ?, ?> field = SpecificationColumnValueFieldFactory.create(requirement);

		return new SpecificationColumn(requirement.getName(), field);
	}
	
	private SpecificationColumn createSpecificationColumn(String displayValue) {
		SpecificationColumnValueField<?, ?, ?> field = new SpecificationColumnTextField();

		return new SpecificationColumn(displayValue, field);
	}
}