package gui.components.specification;

import gui.components.core.PanelManager;
import gui.components.core.TitleBar;

import javax.swing.*;
import java.awt.*;

public class ProductFinderTab extends JPanel {
	private PanelManager panelManager;
	
	public ProductFinderTab(PanelManager panelManager) {
		this.panelManager = panelManager;
		setLayout(new BorderLayout(0, 0));
		
		TitleBar titleBar = new TitleBar();
		titleBar.setTitle("Billigste produkter");
		titleBar.hideButton();
		add(titleBar, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		SpecificationsTab panel = new SpecificationsTab(panelManager);
		scrollPane.setViewportView(panel);
	}
}
