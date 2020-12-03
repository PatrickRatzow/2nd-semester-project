package gui;

import gui.components.core.TabPanel;
import gui.components.specification.SpecificationsProjectTab;

public class ProductFinder extends TabPanel {
	public ProductFinder() {
		setActive("specifications", () -> new SpecificationsProjectTab(this));
	}
}
