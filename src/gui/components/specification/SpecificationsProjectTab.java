package gui.components.specification;

import gui.components.core.TabPanel;
import gui.components.core.TitleBar;

import javax.swing.*;
import java.awt.*;

public class SpecificationsProjectTab extends JPanel {
	private TabPanel panelManager;
	private String previousId;
	
	public SpecificationsProjectTab(TabPanel panelManager) {
		this.panelManager = panelManager;
		previousId = panelManager.getCurrentId();
		setLayout(new BorderLayout(0, 0));
		
		TitleBar titleBar = new TitleBar();
		titleBar.setTitle("Opret projekt");
		titleBar.setButtonName("Gaa tilbage");
		titleBar.addActionListener(e -> {
			String currentId = panelManager.getCurrentId();
			
			panelManager.setActive(previousId);
			panelManager.removePanel(currentId);
		});
		add(titleBar, BorderLayout.NORTH);
		
		JPanel container = new JPanel();
		add(container, BorderLayout.CENTER);
		container.setLayout(new BorderLayout(0, 0));
		
		JPanel titleContainer = new JPanel();
		container.add(titleContainer, BorderLayout.NORTH);
		
		JLabel title = new JLabel("Specifikationer (2/3)");
		title.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		titleContainer.add(title);
		
		JComponent specificationsTab = new SpecificationsTab(panelManager);
		container.add(specificationsTab, BorderLayout.CENTER);
	}
}
