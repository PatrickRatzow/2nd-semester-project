package gui.components.specification;

import entity.Specification;
import gui.components.core.Row;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ChosenSpecificationRow extends Row {
	private JButton removeBtn;
	private Specification specification;
	
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

	public void setSpecification(Specification specification) {
		this.specification = specification;
	}

	public Specification getSpecification() {
		return specification;
	}
}
