package gui.components.specifications.specification;

import controller.SpecificationController;
import entity.Requirement;
import gui.components.core.BackgroundTitle;
import gui.components.core.PanelManager;
import gui.components.core.TitleBar;
import gui.util.Colors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.Map.Entry;

public class SpecificationTab extends JPanel {
	private PanelManager panelManager;
	private String previousId;
	private SpecificationController specificationController;
	private SpecificationColumn nameColumn;
	private SpecificationColumn amountColumn;
	private Map<Requirement, SpecificationColumn> columns;
	private JPanel widthContainer;

	public SpecificationTab(PanelManager panelManager, SpecificationController specificationController) {
		setOpaque(false);
		this.panelManager = panelManager;
		this.specificationController = specificationController;
		previousId = panelManager.getCurrentId();
		List<Requirement> requirements = specificationController.getRequirements();
		columns = new LinkedHashMap<>();

		setLayout(new BorderLayout(0, 0));
		TitleBar title = new TitleBar();
		title.setTitle("Specification");
		title.setButtonName("Gaa Tilbage");
		title.addActionListener(e -> panelManager.setActiveAndRemoveCurrent(previousId));
		add(title, BorderLayout.NORTH);

		BackgroundTitle backgroundTitle = new BackgroundTitle(specificationController.getName());
		add(backgroundTitle, BorderLayout.CENTER);

		JPanel container = new JPanel();
		container.setOpaque(false);
		container.setLayout(new BorderLayout(0, 0));
		backgroundTitle.add(container);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		container.add(scrollPane);

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPane.setViewportView(panel);
		panel.setLayout(new CardLayout());

		widthContainer = new JPanel();
		widthContainer.setOpaque(false);
		widthContainer.setMaximumSize(new Dimension(400, 10000));
		panel.add(widthContainer);
		widthContainer.setLayout(new BoxLayout(widthContainer, BoxLayout.Y_AXIS));

		// Creates name + amount fields
		createSpecificationColumns();

		// Creates the dynamic fields from the requirement
		Iterator<Requirement> iterator = requirements.iterator();
		while (iterator.hasNext()) {
			Requirement requirement = iterator.next();
			SpecificationColumn specificationColumn = createRequirementColumn(requirement);
			widthContainer.add(specificationColumn);
			columns.put(requirement, specificationColumn);
			// Add a spacer if this isn't the last iteration
			if (iterator.hasNext()) {
				widthContainer.add(createSpacer());
			}
		}

		JPanel bottomContainer = new JPanel();
		bottomContainer.setOpaque(false);
		container.add(bottomContainer, BorderLayout.SOUTH);

		JButton save = new JButton("Gem krav");
		save.setBackground(Colors.GREEN.getColor());
		save.setFont(new Font("Segoe UI", Font.PLAIN, 15));
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
			for (Entry<Requirement, SpecificationColumn> column : columns.entrySet()) {
				String value = column.getValue().getStringValue();
				column.getKey().setValueFromSQLValue(value);
			}
			// Set the requirements and save
			specificationController.setRequirements(new LinkedList<>(columns.keySet()));
			specificationController.save();

			// Go back to the previous panel
			panelManager.setActiveAndRemoveCurrent(previousId);
		});
		save.setEnabled(false);
		
		bottomContainer.setLayout(new BorderLayout(0, 0));
		bottomContainer.add(save, BorderLayout.EAST);
		
		Component rigidArea = Box.createRigidArea(new Dimension(0, 5));
		bottomContainer.add(rigidArea, BorderLayout.NORTH);
	}

	public void fillColumnsWithRequirementValues() {
		nameColumn.setStringValue(specificationController.getDisplayName());
		amountColumn.setStringValue(String.valueOf(specificationController.getResultAmount()));

		Map<Requirement, Requirement> requirementsMap = new HashMap<>();
		List<Requirement> requirements = specificationController.getRequirements();
		for (Requirement requirement : requirements) {
			requirementsMap.put(requirement, requirement);
		}

		for (Entry<Requirement, SpecificationColumn> column : columns.entrySet()) {
			String value = requirementsMap.get(column.getKey()).getSQLValue();
			column.getValue().setStringValue(value);
		}
	}

	private void createSpecificationColumns() {
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

	private Component createSpacer() {
		return Box.createRigidArea(new Dimension(0, 20));
	}
}