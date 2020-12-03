package gui.components.specification;

import gui.components.core.TabPanel;

import javax.swing.*;
import java.awt.*;

public class SpecificationsTab extends JPanel {
	private TabPanel panelManager;
	
	public SpecificationsTab(TabPanel panelManager) {
		setOpaque(false);
		this.panelManager = panelManager;
		setLayout(new BorderLayout(0, 0));
	
		ListAndChosenSpecifications listAndChosenSpecifications = new ListAndChosenSpecifications(panelManager);
		add(listAndChosenSpecifications, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JButton continueBtn = new JButton("G\u00E5 videre");
		continueBtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		continueBtn.setForeground(new Color(0, 0, 0));
		continueBtn.setBackground(new Color(152, 251, 152));
		panel.add(continueBtn, BorderLayout.EAST);
	}
}
