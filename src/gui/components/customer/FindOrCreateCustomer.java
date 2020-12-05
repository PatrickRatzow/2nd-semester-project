package gui.components.customer;

import controller.CustomerController;
import controller.ProjectController;
import entity.Customer;
import gui.components.core.PanelManager;
import gui.components.core.TitleBar;
import gui.components.specification.SpecificationsProjectTab;
import gui.util.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class FindOrCreateCustomer extends JPanel {
	private JTextField searchTextField;
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
		String placeholderText = "Telefonnummer";

		TitleBar titleBar = new TitleBar();
		titleBar.setTitle("Find Kunde");
		titleBar.setButtonName("Gaa tilbage");
		titleBar.addActionListener(l -> panelManager.setActiveAndRemoveCurrent(previousId));
		add(titleBar, BorderLayout.NORTH);

		container = new JPanel();
		container.setLayout(new BorderLayout(0, 0));
		add(container, BorderLayout.CENTER);

		JPanel topContainer = new JPanel();
		container.add(topContainer, BorderLayout.NORTH);

		JPanel bottomContainer = new JPanel();
		container.add(bottomContainer, BorderLayout.SOUTH);

		resultContainer = new JPanel();
		container.add(resultContainer, BorderLayout.CENTER);

		searchTextField = new JTextField();
		topContainer.add(searchTextField);
		searchTextField.setColumns(10);
		searchTextField.setForeground(Color.GRAY);
		searchTextField.setText(placeholderText);
		searchTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (searchTextField.getText().equals(placeholderText)) {
					searchTextField.setText("");
					searchTextField.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (searchTextField.getText().isEmpty()) {
					searchTextField.setForeground(Color.GRAY);
					searchTextField.setText(placeholderText);
				}
			}
		});

		btnAddCustomer = new JButton("Tilknyt kunde");
		btnAddCustomer.setBackground(Colors.GREEN.getColor());
		btnAddCustomer.setVisible(false);
		bottomContainer.add(btnAddCustomer);

		JButton btnSearch = new JButton("Anmod");
		btnSearch.addActionListener(e -> customerController.getSearch(searchTextField.getText()));
		topContainer.add(btnSearch);

		JButton btnCreate = new JButton("Opret kunde");
		topContainer.add(btnCreate);

		btnCreate.addActionListener(l -> panelManager.setActive("create_customer", () -> {
			CreateCustomer createCustomer = new CreateCustomer(panelManager);
			createCustomer.addSaveListener(customer -> panelManager.setActive("specifications",
					() -> new SpecificationsProjectTab(panelManager, projectController)));

			return createCustomer;
		}));
		btnSearch.addActionListener(e -> customerController.getSearch(searchTextField.getText()));

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

		btnAddCustomer.addActionListener(e -> {
			projectController.setCustomer(customer);

			panelManager.setActive("specifications",
					() -> new SpecificationsProjectTab(panelManager, projectController));
		});
	}

	private void createCustomerDisplay(Customer customer) {
		resultComponent = new CustomerInformationBox(customer);
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