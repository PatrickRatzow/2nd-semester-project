package gui;

import gui.components.core.TabPanel;
import gui.components.specification.SpecificationsTab;

public class ProductFinder extends TabPanel {
	public ProductFinder() {
		setActive("specifications", () -> new SpecificationsTab(this));
	}
}
