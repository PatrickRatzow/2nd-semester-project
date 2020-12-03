package gui;

import gui.components.core.TabPanel;

public class Customers extends TabPanel{
	
	//Do not use for now it gives error
	public Customers() {
		setActive("customer", () -> new AddCustomer()); 
		//-- should probably be made to another Customer class;
	}
	
}
