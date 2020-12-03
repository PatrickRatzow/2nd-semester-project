package gui.components;

import javax.swing.*;
import java.awt.*;

public class ProjectRow extends Row {
	private JLabel iconLbl;
	private ImageIcon icon;
	
	public ProjectRow() {
		super();
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		iconLbl = new JLabel("");
		panel.add(iconLbl, BorderLayout.WEST);
	}
	
	public void setCompleted(boolean completed) {
		if (completed && icon == null) {
			icon = new ImageIcon(ProjectRow.class.getResource("/javax/swing/plaf/metal/icons/ocean/question.png"));
		}
	
		iconLbl.setIcon(completed ? icon : null);
	}
}
