package gui.components.specification;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gui.components.core.Row;

public class ChosenSpecificationRow extends Row {
	private JButton removeBtn;
	
	public ChosenSpecificationRow(String displayName, boolean even) {
		super(displayName, "Rediger", even);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);
		panel.setOpaque(false);
		panel.setBorder(new EmptyBorder(5, 5, 5, 0));
		
		removeBtn = new JButton("Fjern");
		removeBtn.setBackground(new Color(252, 92, 101));
		removeBtn.setOpaque(true);
		panel.add(removeBtn, BorderLayout.EAST);
		
	}
	
	
	
}
