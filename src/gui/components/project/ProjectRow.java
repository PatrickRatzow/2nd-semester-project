package gui.components.project;

import gui.components.core.Row;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class ProjectRow extends Row {
	private JLabel iconLbl;
	private ImageIcon icon;

	public ProjectRow() {
		super();
		
		setMaximumSize(new Dimension(32746, 50));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		iconLbl = new JLabel("");
		panel.add(iconLbl, BorderLayout.WEST);
	}
	
	public void setCompleted(boolean completed) {
		if (completed && icon == null) {
			try {
				InputStream stream = getClass().getResourceAsStream("/resources/images/checkmark.png");
				icon = new ImageIcon(ImageIO.read(stream));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
		iconLbl.setIcon(completed ? icon : null);
	}
}
