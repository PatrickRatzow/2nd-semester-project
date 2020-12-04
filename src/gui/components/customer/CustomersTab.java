package gui.components.customer;

import controller.CustomerController;
import entity.Customer;
import gui.components.core.PanelManager;
import gui.components.core.Row;
import gui.components.core.TitleBar;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CustomersTab extends JPanel {
	private PanelManager panelManager;
	private CustomerController customerController;
	private JPanel container;
	private Map<Customer, Row> rows;
	
	public CustomersTab(PanelManager panelManager) {
		rows = new HashMap<>();
		customerController = new CustomerController();
		
		this.panelManager = panelManager;
		setLayout(new BorderLayout(0, 0));
		
		TitleBar titleBar = new TitleBar();
		titleBar.setTitle("Kunder");
		titleBar.setButtonName("Opret Kunde");
		titleBar.addActionListener(e -> {
			String currentId = panelManager.getCurrentId();
			
			panelManager.setActive("create_customer", () -> {
				CreateCustomer createCustomer = new CreateCustomer(panelManager);
				createCustomer.addSaveListener(customer -> {
					createRow(customer);
					
					panelManager.setActive(currentId);
					panelManager.removePanel("create_customer");
				});
				
				return createCustomer;
			});
		});
			
		JTextField searchBar = titleBar.createSearchBar("Kunde telefon/email");
		searchBar.addActionListener(e -> {
			String text = searchBar.getText();
			if (text.isEmpty()) {
				customerController.getAll();
			} else {
				customerController.getSearch(text);
			}
		});
		add(titleBar, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		container = new JPanel();
		scrollPane.setViewportView(container);
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		customerController.addFindListener(customers -> {
			container.removeAll();
			rows.clear();
			for (Customer customer : customers) {
				createRow(customer);
			}
			container.repaint();
		});
	
		customerController.getAll();
	}
	
	private void addRowListener(Customer customer, Row row) {
		row.addActionListener(e -> {
			String currentId = panelManager.getCurrentId();
			
			panelManager.setActive("update_customer", () -> {
				UpdateCustomer updateCustomer = new UpdateCustomer(panelManager, customer);
				updateCustomer.addSaveListener(c -> {
					row.setTitleText(c.getFirstName() + " " + c.getLastName() + " (tlf. " + c.getPhoneNumber() + ")");
					row.removeAllActionListeners();
					addRowListener(c, row);
					
					panelManager.setActive(currentId);
					panelManager.removePanel("update_customer");
				});
				
				return updateCustomer;
			});
		});
	}
	private void createRow(Customer customer) {
		if (rows.get(customer) != null) return;
		
		boolean even = (rows.size() + 1) % 2 == 0;
		Row row = new Row(even);
		row.setTitleText(customer.getFirstName() + " " + customer.getLastName() + " (tlf. " + customer.getPhoneNumber() + ")");
		row.setButtonText("Aaben");
		addRowListener(customer, row);

		rows.put(customer, row);
		container.add(row);
	}
}
