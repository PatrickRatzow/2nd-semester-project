package gui;

import gui.components.core.PanelManager;
import gui.components.customer.CustomersTab;

public class Customers extends PanelManager {
	public Customers() {
		setActive("customers", () -> new CustomersTab(this));
	}
}
