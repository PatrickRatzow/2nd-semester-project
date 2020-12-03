package gui.components.customer;

import controller.CustomerController;
import entity.Customer;
import exception.DataAccessException;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class CreateProjectCustomerGui extends JPanel {
	private JTextField searchTextField;
	private CustomerController customerController;
	private JComponent resultComponent;

	private void searchCustomer(String phoneNumber) {
		try {
			Customer customer = customerController.findByPhoneNumber(phoneNumber);
			if (resultComponent != null) {
				remove(resultComponent);
			}
			if (customer != null) {
				resultComponent = new CustomerInformationGui(customer);
				add(resultComponent, "cell 0 3,grow");
			} else {
				resultComponent = new JLabel(phoneNumber + " does not exist");
				resultComponent.setFont(new Font("Tahoma", Font.PLAIN, 18));
				resultComponent.setForeground(Color.RED);
				add(resultComponent, "cell 0 2");
			}
			
			revalidate();
			repaint();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	public CreateProjectCustomerGui() {
		customerController = new CustomerController();

		JLabel lblNewLabel = new JLabel("Kunde");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		setLayout(new MigLayout("", "[373px,grow]", "[22px][][19px][grow]"));
		add(lblNewLabel, "cell 0 0,growx,aligny top");

		searchTextField = new JTextField();
		searchTextField.setColumns(10);
		add(searchTextField, "flowx,cell 0 1,alignx left,aligny top");

		JButton btnSearch = new JButton("Anmod");
		btnSearch.addActionListener(e -> searchCustomer(searchTextField.getText()));

		add(btnSearch, "cell 0 1");

		JButton btnCreate = new JButton("Opret kunde");
		add(btnCreate, "cell 0 1");
	}
}