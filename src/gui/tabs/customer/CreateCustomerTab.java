package gui.tabs.customer;

import controller.CustomerController;
import gui.components.core.PanelManager;
import gui.components.core.TitleBar;
import gui.components.customer.CreateCustomer;

import javax.swing.*;
import java.awt.*;

public class CreateCustomerTab extends JComponent {
	public CreateCustomerTab(PanelManager panelManager, CustomerController customerController, String title, String buttonName) {
		setLayout(new BorderLayout(0, 0));
		
		String previousId = panelManager.getCurrentId();

		TitleBar titleBar = new TitleBar();
		titleBar.setTitle(title);
		titleBar.setButtonName(buttonName);
		titleBar.addActionListener(e -> panelManager.setActiveAndRemoveCurrent(previousId));
		add(titleBar, BorderLayout.NORTH);

		CreateCustomer createCustomer = new CreateCustomer(customerController);
		add(createCustomer, BorderLayout.CENTER);
	}
}
