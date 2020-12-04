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
import java.util.List;
import java.util.Map;

public class SpecificationTab extends JPanel {
	private PanelManager panelManager;
	private String previousId;
	private SpecificationController specificationController;

	public SpecificationTab(PanelManager panelManager, Specification specification) {
		this.panelManager = panelManager;
		previousId = panelManager.getCurrentId();
		specificationController = new SpecificationController(specification);
		List<Requirement> requirements = specificationController.getRequirements();
		
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
		save.addActionListener(e -> System.out.println("Krav er gemt"));
		
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
		JLabel titleLabel = new JLabel(specification.getName());
		titleContainer.add(titleLabel);
		
			titleLabel.setFont(new Font(titleLabel.getFont().toString(), Font.PLAIN, 20));
			
			
			Font font = titleLabel.getFont();
			Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
			attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			titleLabel.setFont(font.deriveFont(attributes));


		widthContainer.add(createSpacer());

		widthContainer.add(createSpecificationRow("Navn"));
		widthContainer.add(createSpacer());
		widthContainer.add(createSpecificationRow("Antal"));
		widthContainer.add(createSpacer());
		
		
		for (Requirement requirement : requirements) {
			SpecificationColumn specificationColumn = createRequirementColumn(requirement);

			widthContainer.add(specificationColumn);
			widthContainer.add(createSpacer());

		}
	}
	
	private Component createSpacer() {
		return Box.createRigidArea(new Dimension(0, 20));
	}
	
	private SpecificationColumn createRequirementColumn(Requirement requirement) {
		SpecificationColumn rows = new SpecificationColumn();
		rows.setTitleName(requirement.getName());

		return rows;
	}
	private SpecificationColumn createSpecificationRow(String displayValue) {	
		SpecificationColumn rows = new SpecificationColumn();
		rows.setTitleName(displayValue);
		
		return rows;
	}
	
}