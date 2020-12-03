package gui.components.customer;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.*;

public class CreateProjectCustomerGui extends JPanel {

	/**
	 * Create the panel.
	 */
	public CreateProjectCustomerGui() {
		
		JLabel lblNewLabel = new JLabel("Kunde");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 373, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(67, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addContainerGap(268, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
}
