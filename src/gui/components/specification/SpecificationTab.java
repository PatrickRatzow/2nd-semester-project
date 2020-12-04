package gui.components.specification;

import controller.SpecificationController;
import entity.Requirement;
import gui.components.core.PanelManager;
import gui.components.core.TitleBar;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class SpecificationTab extends JPanel {
	private PanelManager panelManager;
	private String previousId;
	private SpecificationController specificationController;
	private SpecificationColumn nameColumn;
	private SpecificationColumn amountColumn;

	public SpecificationTab(PanelManager panelManager, SpecificationController specificationController) {
		this.panelManager = panelManager;
		this.specificationController = specificationController;
		previousId = panelManager.getCurrentId();
		List<Requirement> requirements = specificationController.getRequirements();
		String name = specificationController.getDislayName();
		
		
		List<Requirement> saveRequirements = new LinkedList<Requirement>();
		
		
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
		save.setBackground(Color.GREEN);
		save.setForeground(Color.BLACK);
		save.setHorizontalAlignment(SwingConstants.RIGHT);
		buttomBar.add(save);
		//Send reuqirements have been saved, and back to Specifications window.
		
		save.addActionListener(e -> {
			
			String resultNameInTextField = nameColumn.getTextField().getText();
			String resultAmountInTextField = amountColumn.getTextField().getText();	
				
			int parseAmount = Integer.parseInt(resultAmountInTextField);
			System.out.println("Krav er gemt");
			specificationController.setDisplayName(resultNameInTextField);
			specificationController.setResultAmount(parseAmount);
			specificationController.setRequirements(saveRequirements);
			System.out.println(specificationController.getDislayName());
			System.out.println(specificationController.getResultAmount());
			System.out.println(specificationController.getRequirements());
			
			specificationController.save();
			String currentId = panelManager.getCurrentId();
			
			panelManager.setActive(previousId);
			panelManager.removePanel(currentId);
		});
		
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
			
			
			Font font = titleLabel.getFont();
			Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
			attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			titleLabel.setFont(font.deriveFont(attributes));

		createSpecificationColumns(widthContainer);			
		
		for (Requirement requirement : requirements) {
			SpecificationColumn specificationColumn = createRequirementColumn(requirement);

			widthContainer.add(specificationColumn);
			widthContainer.add(createSpacer());
			saveRequirements.add(requirement);
		}
		
		System.out.println("Added requirements: " + saveRequirements);
	}
	
	private Component createSpacer() {
		return Box.createRigidArea(new Dimension(0, 20));
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
	
	private SpecificationColumn createRequirementColumn(Requirement requirement) {
		SpecificationColumn rows = new SpecificationColumn();
		rows.setTitleName(requirement.getName());

		return rows;
	}
	private SpecificationColumn createSpecificationColumn(String displayValue) {	
		SpecificationColumn rows = new SpecificationColumn();
		rows.setTitleName(displayValue);
		
		return rows;
	}
	
}