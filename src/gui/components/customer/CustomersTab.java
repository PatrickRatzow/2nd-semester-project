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
	
	public CustomersTab(TabPanel panelManager) {
		customerController = new CustomerController();
		
		this.panelManager = panelManager;
		setLayout(new BorderLayout(0, 0));
		
		TitleBar titleBar = new TitleBar();
		titleBar.setTitle("Kunder");
		titleBar.setButtonName("Opret Kunde");
		JTextField searchBar = titleBar.createSearchBar("Kunde telefon/email");
		searchBar.addActionListener(e -> search(searchBar.getText()));
		add(titleBar, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		JPanel container = new JPanel();
		scrollPane.setViewportView(container);
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		Customer customer = new Customer();
		customer.setFirstName("Allan");
		customer.setLastName("Jensen");
		customer.setPhoneNumber("45454545");
		
		container.add(createRow(customer));
	}
	
	private Row createRow(Customer customer) {
		Row row = new Row();
		row.setTitleText(customer.getFirstName() + " " + customer.getLastName() + " (tlf. " + customer.getPhoneNumber() + ")");
		row.setButtonText("Aaben");
		
		return row;
	}
	
	private void search(String search) {
		
	}
}
