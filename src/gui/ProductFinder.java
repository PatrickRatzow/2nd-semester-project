package gui;

import javax.swing.JPanel;

import gui.components.core.TabPanel;
import gui.components.specification.SpecificationsProjectTab;

public class ProductFinder extends TabPanel {
	
	
	//Just using this for a panelManager atm, change later;
	public ProductFinder() {
		setActive("specifications", () -> new SpecificationsProjectTab(this));
	}
}
