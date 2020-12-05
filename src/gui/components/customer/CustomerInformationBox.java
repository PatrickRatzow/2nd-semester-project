package gui.components.customer;

import entity.Customer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class CustomerInformationBox extends JPanel {
	private JPanel panel;
	private Customer customer;
	
	public CustomerInformationBox(Customer customer) {
		setBackground(Color.GRAY);
		setLayout(new BorderLayout(0, 0));
		setBackground(Color.RED);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		panel = new JPanel();
		panel.setMinimumSize(new Dimension(400, 10));
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		scrollPane.setViewportView(panel);
		
		JLabel titleLbl = new JLabel("Kunde");
		titleLbl.setBorder(new EmptyBorder(5, 5, 5, 5));
		titleLbl.setFont(new Font("Tahoma", Font.BOLD, 18));
		add(titleLbl, BorderLayout.NORTH);
		
		List<String[]> rows = new LinkedList<>();
		rows.add(new String[]{ "Fornavn", customer.getFirstName() });
		rows.add(new String[]{ "Efternavn", customer.getLastName() });
		rows.add(new String[]{ "Addresse", customer.getAddress().getStreetName() + " " +
				customer.getAddress().getStreetNumber() });
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
		panel.setMaximumSize(new Dimension(10000, 60));
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JLabel title = new JLabel(titleText);
		title.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(title);
		
		JLabel body = new JLabel(bodyText);
		body.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(body);
		
		return panel;
	}
}