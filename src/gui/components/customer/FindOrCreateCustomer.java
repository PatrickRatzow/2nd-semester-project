package gui.components.customer;

import controller.CustomerController;
import controller.ProjectController;
import entity.Customer;
import gui.components.core.BackgroundTitle;
import gui.components.core.PanelManager;
import gui.components.core.SearchField;
import gui.components.core.TitleBar;
import gui.components.specification.SpecificationsProjectTab;
import gui.util.Colors;

import javax.swing.*;
import java.awt.*;

public class FindOrCreateCustomer extends JPanel {
	private SearchField searchTextField;
	private CustomerController customerController;
	private JComponent resultComponent;
	private PanelManager panelManager;
	private String previousId;
	private ProjectController projectController;
	private JPanel container;
	private JButton btnAddCustomer;
	private JPanel resultContainer;
	private Customer customer;

	public FindOrCreateCustomer(PanelManager panelManager, ProjectController projectController) {
		this.projectController = projectController;
		customerController = new CustomerController();
		previousId = panelManager.getCurrentId();
		setLayout(new BorderLayout(0, 0));

		TitleBar titleBar = new TitleBar();
		titleBar.setTitle("Opret Projekt");
		titleBar.setButtonName("Gaa tilbage");
		titleBar.addActionListener(l -> panelManager.setActiveAndRemoveCurrent(previousId));
		add(titleBar, BorderLayout.NORTH);

		BackgroundTitle backgroundTitle = new BackgroundTitle("Kunde (1/3)");
		add(backgroundTitle, BorderLayout.CENTER);
		
		container = new JPanel();
		container.setOpaque(false);
		container.setLayout(new BorderLayout(0, 0));
		backgroundTitle.add(container);

		JPanel topContainer = new JPanel();
		topContainer.setOpaque(false);
		container.add(topContainer, BorderLayout.NORTH);

		JPanel bottomContainer = new JPanel();
		bottomContainer.setOpaque(false);
		container.add(bottomContainer, BorderLayout.SOUTH);

		resultContainer = new JPanel();
		resultContainer.setOpaque(false);
		container.add(resultContainer, BorderLayout.CENTER);

		searchTextField = new SearchField();
		searchTextField.setColumns(11);
		searchTextField.setForeground(Color.GRAY);
		searchTextField.setPlaceholder("Telefonnummer");
		topContainer.add(searchTextField);

		btnAddCustomer = new JButton("Tilknyt kunde");
		btnAddCustomer.setBackground(Colors.GREEN.getColor());
		btnAddCustomer.setVisible(false);
		btnAddCustomer.addActionListener(e -> {
			projectController.setCustomer(customer);

			panelManager.setActive("specifications",
					() -> new SpecificationsProjectTab(panelManager, projectController));
		});
		bottomContainer.add(btnAddCustomer);

		JButton btnSearch = new JButton("Anmod");
		btnSearch.addActionListener(e -> customerController.getSearch(searchTextField.getText()));
		topContainer.add(btnSearch);

		JButton btnCreate = new JButton("Opret kunde");
		btnCreate.addActionListener(l -> panelManager.setActive("create_customer", () -> {
			CreateCustomer createCustomer = new CreateCustomer(panelManager);
			createCustomer.addSaveListener(customer -> 
				panelManager.setActive("specifications",
					() -> new SpecificationsProjectTab(panelManager, projectController)));

			return createCustomer;
		}));
		topContainer.add(btnCreate);

		customerController.addFindListener(customers -> {
			if (resultComponent != null) {
				resultContainer.remove(resultComponent);
			}
			if (!customers.isEmpty()) {
				createCustomerDisplay(customers.get(0));
			} else {
				createNoResultDisplay();
			}
			revalidate();
			repaint();
		});
		customerController.addSaveListener(customer -> {
			projectController.setCustomer(customer);

			panelManager.setActive("specifications",
					() -> new SpecificationsProjectTab(panelManager, projectController));
		});
	}

	private void createCustomerDisplay(Customer customer) {
		resultComponent = new CustomerInformationBox(customer);
		Dimension dimension = resultComponent.getPreferredSize();
		dimension.width = 400;
		resultComponent.setPreferredSize(dimension);
		resultContainer.add(resultComponent);
		
		this.customer = customer;

		btnAddCustomer.setVisible(true);
	}

	private void createNoResultDisplay() {
		resultComponent = new JLabel(searchTextField.getText() + " eksistere ikke.");
		resultComponent.setFont(new Font("Tahoma", Font.PLAIN, 18));
		resultComponent.setForeground(Colors.RED.getColor());
		resultContainer.add(resultComponent);
		
		this.customer = null;

		btnAddCustomer.setVisible(false);
	}
}