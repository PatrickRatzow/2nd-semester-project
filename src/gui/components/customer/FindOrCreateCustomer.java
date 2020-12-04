package gui.components.customer;

import controller.CustomerController;
import controller.ProjectController;
import entity.Customer;
import gui.components.core.PanelManager;
import gui.components.core.TitleBar;
import gui.components.specification.SpecificationsProjectTab;
import net.miginfocom.swing.MigLayout;

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
	private JPanel panel;

	public FindOrCreateCustomer(PanelManager panelManager, ProjectController projectController) {
		this.projectController = projectController;
		customerController = new CustomerController();
		previousId = panelManager.getCurrentId();
		setLayout(new BorderLayout(0, 0));
		String placeholderText = "Telefonnummer";

		TitleBar titleBar = new TitleBar();
		titleBar.setTitle("Find Kunde");
		titleBar.setButtonName("Gaa tilbage");
		titleBar.addActionListener(l -> {
			String currentId = panelManager.getCurrentId();
			
			panelManager.setActive(previousId);
			panelManager.removePanel(currentId);
		});
		add(titleBar, BorderLayout.NORTH);
		
		panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new MigLayout("", "[439.00px]", "[23px][][][grow]"));

		JLabel lblNewLabel = new JLabel("Kunde");
		panel.add(lblNewLabel, "cell 0 0,alignx left,aligny top");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));

		searchTextField = new JTextField();
		panel.add(searchTextField, "flowx,cell 0 1");
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

		JButton btnSearch = new JButton("Anmod");
		btnSearch.addActionListener(e -> customerController.getSearch(searchTextField.getText()));
		panel.add(btnSearch, "cell 0 1,alignx left,aligny top");

		JButton btnCreate = new JButton("Opret kunde");
		panel.add(btnCreate, "cell 0 1,alignx left,aligny top");
		btnCreate.addActionListener(l -> panelManager.setActive("create_customer",
				() -> {
					CreateCustomer createCustomer = new CreateCustomer(panelManager);
					createCustomer.addSaveListener(customer ->
							panelManager.setActive("specifications",
									() -> new SpecificationsProjectTab(panelManager)));

					return createCustomer;
				}
		));
		btnSearch.addActionListener(e -> customerController.getSearch(searchTextField.getText()));

		customerController.addFindListener(customers -> {
			if (resultComponent != null) {
				panel.remove(resultComponent);
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
			
		});
	}
	
	private void createCustomerDisplay(Customer customer) {
		resultComponent = new JPanel();
		panel.add(resultComponent, "cell 0 3,grow");
		resultComponent.setLayout(new BorderLayout());
		CustomerInformationBox customerInformationBox = new CustomerInformationBox(customer);
		resultComponent.add(customerInformationBox, BorderLayout.WEST);
	}
	
	private void createNoResultDisplay() {
		resultComponent = new JLabel(searchTextField.getText() + " eksistere ikke.");
		resultComponent.setFont(new Font("Tahoma", Font.PLAIN, 18));
		resultComponent.setForeground(Color.RED);
		panel.add(resultComponent, "cell 0 2");
	}

}