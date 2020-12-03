package gui.components.customer;

import entity.Customer;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class CustomerInformationGui extends JPanel {
	private JPanel panel;
	private Customer customer;
	
	public CustomerInformationGui(Customer customer) {
		setBackground(Color.GRAY);
		setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Kunde");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		add(lblNewLabel, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		scrollPane.setViewportView(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		List<String[]> rows = new LinkedList<>();
		rows.add(new String[]{ "Fornavn", customer.getFirstName() });
		rows.add(new String[]{ "Efternavn", customer.getLastName() });
		rows.add(new String[]{ "Addresse", customer.getAddress().getStreetName() + customer.getAddress().getStreetNumber() });
		rows.add(new String[]{ "By", customer.getAddress().getCity() });
		rows.add(new String[]{ "Postnummer", String.valueOf(customer.getAddress().getZipCode()) });
		rows.add(new String[]{ "Email", customer.getEmail() });
		
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