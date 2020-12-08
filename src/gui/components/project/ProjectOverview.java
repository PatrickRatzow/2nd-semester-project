package gui.components.project;

import controller.ProjectController;
import gui.components.customer.CustomerInformationBox;
import gui.components.product.ProductsBox;
import gui.util.Colors;

import javax.swing.*;
import java.awt.*;

public class ProjectOverview extends JPanel {
	public ProjectOverview(ProjectController projectController) {
		setOpaque(false);
		setLayout(new CardLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout(5, 0));
		add(panel, "name_1596068453270700");
		
		CustomerInformationBox customerBox = new CustomerInformationBox(projectController.getCustomer());
		panel.add(customerBox, BorderLayout.WEST);
	
		ProductsBox productsBox = new ProductsBox(projectController.getOrderController());
		productsBox.setBackground(Colors.PRIMARY.getColor());
		productsBox.setPriceBackground(Colors.PRIMARY.getColor());
		panel.add(productsBox, BorderLayout.CENTER);
		
		ProjectDetails details = new ProjectDetails();
		panel.add(details, BorderLayout.EAST);
	}
}
