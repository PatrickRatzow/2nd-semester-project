package gui;

import gui.components.core.PanelManager;
import gui.components.specification.ProductFinderTab;

public class ProductFinder extends PanelManager {
	public ProductFinder() {
		setActive("specifications", () -> new ProductFinderTab(this));
	}
}
