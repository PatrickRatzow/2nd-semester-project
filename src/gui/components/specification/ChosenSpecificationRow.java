package gui.components.specification;

import gui.components.core.Row;
import gui.util.Colors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

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
		removeBtn.setBackground(Colors.RED.getColor());
		removeBtn.setOpaque(true);
		panel.add(removeBtn, BorderLayout.EAST);
	}

	public void addRemoveButtonActionListener(ActionListener listener) {
		removeBtn.addActionListener(listener);
	}
}
