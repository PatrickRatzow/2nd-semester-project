package gui.components.specification;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import gui.components.core.TabPanel;

public class SpecificationsTab extends JPanel {

	private TabPanel panelManager;
	
	public SpecificationsTab(TabPanel panelManager) {
		this.panelManager = panelManager;
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JButton continueBtn = new JButton("G\u00E5 videre");
		continueBtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		continueBtn.setForeground(new Color(0, 0, 0));
		continueBtn.setBackground(new Color(152, 251, 152));
		panel.add(continueBtn, BorderLayout.EAST);
		
		ListAndChosenSpecifications listAndChosenSpecifications = new ListAndChosenSpecifications((TabPanel) null);
		add(listAndChosenSpecifications, BorderLayout.CENTER);
	}

}
