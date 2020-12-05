package gui.components.customer;

import entity.Customer;
import gui.components.core.BackgroundTitle;
import gui.util.Colors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CustomerInformationBox extends BackgroundTitle {
	private JPanel panel;
	private Customer customer;
	
	public CustomerInformationBox(Customer customer) {
		setBackground(Colors.PRIMARY.getColor());
		setTitle("Kunde");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		add(scrollPane);
		
		panel = new JPanel();
		panel.setOpaque(false);
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel.setMinimumSize(new Dimension(400, 10));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		scrollPane.setViewportView(panel);
		
		List<String[]> rows = new LinkedList<>();
		rows.add(new String[]{ "Fornavn", customer.getFirstName() });
		rows.add(new String[]{ "Efternavn", customer.getLastName() });
		rows.add(new String[]{ "Addresse", customer.getAddress().getStreetName() + " " +
				customer.getAddress().getStreetNumber() });
		rows.add(new String[]{ "By", customer.getAddress().getCity() });
		rows.add(new String[]{ "Postnummer", String.valueOf(customer.getAddress().getZipCode()) });
		rows.add(new String[]{ "Email", customer.getEmail() });
		
		Iterator<String[]> iterator = rows.iterator();
		while (iterator.hasNext()) {
			String[] strings = iterator.next();
			panel.add(createRow(strings[0], strings[1]));
			
			if (iterator.hasNext()) {
				panel.add(Box.createRigidArea(new Dimension(0, 5)));
			}
		}
	}

	private JPanel createRow(String titleText, String bodyText) {
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setMaximumSize(new Dimension(10000, 1000));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel title = new JLabel(titleText);
		title.setBackground(Color.LIGHT_GRAY);
		title.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 12));
		panel.add(title);
		
		JLabel body = new JLabel(bodyText);
		body.setFont(new Font("Segoe UI", Font.BOLD, 16));
		panel.add(body);
		
		return panel;
	}
}