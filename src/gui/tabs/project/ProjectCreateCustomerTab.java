package gui.tabs.project;

import controller.CustomerController;
import gui.components.core.BackgroundTitle;
import gui.components.core.PanelManager;
import gui.components.core.TitleBar;
import gui.components.customer.CreateCustomer;

import javax.swing.*;
import java.awt.*;

public class ProjectCreateCustomerTab extends JComponent {
	public ProjectCreateCustomerTab(PanelManager panelManager, CustomerController customerController) {
		String previousId = panelManager.getCurrentId();
		setLayout(new BorderLayout(0, 0));
		
		TitleBar titleBar = new TitleBar();
		titleBar.setTitle("Opret Projekt");
		titleBar.setButtonName("G� Tilbage");
		titleBar.addActionListener(l -> panelManager.setActiveAndRemoveCurrent(previousId));
		add(titleBar, BorderLayout.NORTH);
		
		BackgroundTitle backgroundContainer = new BackgroundTitle("Kunde (1/3)");
		add(backgroundContainer, BorderLayout.CENTER);
		
		CreateCustomer createCustomer = new CreateCustomer(customerController);
		backgroundContainer.add(createCustomer);
	}
}
