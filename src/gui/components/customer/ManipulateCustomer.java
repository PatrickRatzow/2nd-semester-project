package gui.components.customer;

import controller.CustomerController;
import entity.Customer;
import gui.components.core.PanelManager;
import gui.components.core.TitleBar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Consumer;

public abstract class ManipulateCustomer extends JPanel {
	protected ManipulateCustomerColumn firstName;
	protected ManipulateCustomerColumn address;
	protected ManipulateCustomerColumn city;
	protected ManipulateCustomerColumn phoneNumber;
	protected ManipulateCustomerColumn lastName;
	protected ManipulateCustomerColumn addressNumber;
	protected ManipulateCustomerColumn zipCode;
	protected ManipulateCustomerColumn email;
	
	protected CustomerController customerController; 
	protected PanelManager panelManager;
	protected String previousId;
	protected JButton btnAdd;
	
	public ManipulateCustomer(PanelManager panelManager, String title, String buttonName) {
		this.panelManager = panelManager;
		previousId = panelManager.getCurrentId();
		customerController = new CustomerController();
		
		setLayout(new BorderLayout(0, 0));
		
		TitleBar titleBar = new TitleBar();
		titleBar.setTitle(title);
		titleBar.setButtonName(buttonName);
		titleBar.addActionListener(e -> {
			String currentId = panelManager.getCurrentId();
			
			panelManager.setActive(previousId);
			panelManager.removePanel(currentId);
		});
		add(titleBar, BorderLayout.NORTH);
		
		JPanel buttonContainer = new JPanel();
		add(buttonContainer, BorderLayout.SOUTH);
		
		btnAdd = new JButton();
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		buttonContainer.add(btnAdd);
		
		JPanel container = new JPanel();
		container.setBorder(new EmptyBorder(10, 10, 50, 10));
		add(container, BorderLayout.CENTER);
		container.setLayout(new GridLayout(0, 2, 30, 0));
		
		JPanel leftColumn = new JPanel();
		container.add(leftColumn);
		leftColumn.setLayout(new BoxLayout(leftColumn, BoxLayout.Y_AXIS));
		
		Dimension spacing = new Dimension(0, 5);
		firstName = new ManipulateCustomerColumn("Fornavn");
		leftColumn.add(firstName);
		
		Component firstNameSpacer = Box.createRigidArea(spacing);
		leftColumn.add(firstNameSpacer);
		
		address = new ManipulateCustomerColumn("Adresse");
		leftColumn.add(address);
		
		Component addressSpacer = Box.createRigidArea(spacing);
		leftColumn.add(addressSpacer);
		
		city = new ManipulateCustomerColumn("By");
		leftColumn.add(city);
		
		Component citySpacer = Box.createRigidArea(spacing);
		leftColumn.add(citySpacer);
		
		phoneNumber = new ManipulateCustomerColumn("Telefonnummer");
		leftColumn.add(phoneNumber);
		
		JPanel rightColumn = new JPanel();
		container.add(rightColumn);
		rightColumn.setLayout(new BoxLayout(rightColumn, BoxLayout.Y_AXIS));
		
		lastName = new ManipulateCustomerColumn("Efternavn");
		rightColumn.add(lastName);
		
		Component lastNameSpacer = Box.createRigidArea(spacing);
		rightColumn.add(lastNameSpacer);
		
		addressNumber = new ManipulateCustomerColumn("Adresse nummer");
		rightColumn.add(addressNumber);
		
		Component addressNumberSpacer = Box.createRigidArea(spacing);
		rightColumn.add(addressNumberSpacer);
		
		zipCode = new ManipulateCustomerColumn("Postnummer");
		rightColumn.add(zipCode);
		
		Component zipCodeSpacer = Box.createRigidArea(spacing);
		rightColumn.add(zipCodeSpacer);
		
		email = new ManipulateCustomerColumn("Email");
		rightColumn.add(email);
		
		btnAdd.addActionListener(l -> onSave());
	}
	
	public void addSaveListener(Consumer<Customer> listener) {
		customerController.addSaveListener(listener);
	}

	public abstract void onSave();
}
