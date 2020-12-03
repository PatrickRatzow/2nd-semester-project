package gui.components.customer;

import controller.CustomerController;
import entity.Customer;
import gui.components.core.Row;
import gui.components.core.TabPanel;
import gui.components.core.TitleBar;

import javax.swing.*;
import java.awt.*;

public class CustomersTab extends JPanel {
	private TabPanel panelManager;
	private CustomerController customerController;
	private JPanel container;
	
	public CustomersTab(TabPanel panelManager) {
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
			for (Customer customer : customers) {
				container.add(createRow(customer));
			}
			container.repaint();
		});
	
		customerController.getAll();
	}
	
	private Row createRow(Customer customer) {
		Row row = new Row();
		row.setTitleText(customer.getFirstName() + " " + customer.getLastName() + " (tlf. " + customer.getPhoneNumber() + ")");
		row.setButtonText("Aaben");
		
		return row;
	}
}
