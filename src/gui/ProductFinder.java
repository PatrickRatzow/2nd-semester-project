package gui;

import gui.components.core.PanelManager;
import gui.components.specification.SpecificationsTab;

public class ProductFinder extends PanelManager {
	public ProductFinder() {
		setActive("specifications", () -> new SpecificationsTab(this));
	}
}
