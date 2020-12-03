package gui;

import gui.components.core.TabPanel;
import gui.components.customer.CustomersTab;

public class Customers extends TabPanel {
	public Customers() {
		setActive("customers", () -> new CustomersTab(this));
	}
}
