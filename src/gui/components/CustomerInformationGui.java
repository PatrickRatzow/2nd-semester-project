package gui.components;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class CustomerInformationGui extends JPanel {
	private JPanel panel;
	
	public CustomerInformationGui() {
		setBackground(Color.GRAY);
		setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Kunde");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		add(lblNewLabel, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		List<String[]> rows = new LinkedList<>();
		rows.add(new String[]{ "Fornavn", "Anders" });
		rows.add(new String[]{ "Efternavn", "Andersen" });
		rows.add(new String[]{ "Email", "email@email.xd" });
		
		for (String[] strings : rows) {
			panel.add(createRow(strings[0], strings[1]));
		}
	}

	private JPanel createRow(String titleText, String bodyText) {
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel title = new JLabel(titleText);
		title.setFont(new Font("Tahoma", Font.BOLD, 10));
		panel.add(title);
		
		JLabel body = new JLabel(bodyText);
		panel.add(body);
		
		return panel;
	}
}
