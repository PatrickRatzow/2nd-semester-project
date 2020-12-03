package gui.components.specification;

import gui.components.core.TabPanel;

import javax.swing.*;

public class SpecificationProjectTab extends JPanel {
	private TabPanel panelManager;
	private String previousId;
	
	public SpecificationProjectTab(TabPanel panelManager) {
		this.panelManager = panelManager;
		previousId = panelManager.getCurrentId();
	}
}
