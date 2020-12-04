package gui.components.customer;

import controller.CustomerController;
import entity.Customer;
import gui.components.core.PanelManager;
import gui.components.core.Row;
import gui.components.core.TitleBar;

import javax.swing.*;
import java.awt.*;

public class CustomersTab extends JPanel {
	private PanelManager panelManager;
	private CustomerController customerController;
	private JPanel container;
	
	public CustomersTab(PanelManager panelManager) {
		customerController = new CustomerController();
		
		this.panelManager = panelManager;
		setLayout(new BorderLayout(0, 0));
		
		TitleBar titleBar = new TitleBar();
		titleBar.setTitle("Kunder");
		titleBar.setButtonName("Opret Kunde");
		titleBar.addActionListener(e -> 
			panelManager.setActive("create_customer", () -> new CreateCustomer(panelManager)));
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
			int size = customers.size();
			for (int i = 0; i < size; i++) {
				Customer customer = customers.get(i);
				container.add(createRow(customer, (i + 1) % 2 == 0));
			}
			container.repaint();
		});
	
		customerController.getAll();
	}
	
	private Row createRow(Customer customer, boolean even) {
		Row row = new Row(even);
		row.setTitleText(customer.getFirstName() + " " + customer.getLastName() + " (tlf. " + customer.getPhoneNumber() + ")");
		row.setButtonText("Aaben");
		row.addActionListener(e -> panelManager.setActive("update_customer", 
				() -> new UpdateCustomer(panelManager, customer)));
		
		return row;
	}
}
