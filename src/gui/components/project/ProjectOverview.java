package gui.components.project;

import controller.ProjectController;
import gui.components.core.BackgroundTitle;
import gui.components.customer.CustomerInformationBox;
import gui.components.product.ProductsBox;
import gui.util.Colors;
import model.OrderLine;
import model.Price;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class ProjectOverview extends BackgroundTitle {
	public ProjectOverview(ProjectController projectController) {
		setTitle("Oversigt (3/3)");
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout(5, 0));
		add(panel);
		
		CustomerInformationBox customerBox = new CustomerInformationBox(projectController.getCustomer());
		panel.add(customerBox, BorderLayout.WEST);
		
		Collection<OrderLine> orderLines = projectController.getOrderLines();
		Price price = projectController.getOrderPrice();
	
		ProductsBox productsBox = new ProductsBox(orderLines, price);
		productsBox.setBackground(Colors.PRIMARY.getColor());
		productsBox.setPriceBackground(Colors.PRIMARY.getColor());
		panel.add(productsBox, BorderLayout.CENTER);
		
		ProjectDetails details = new ProjectDetails();
		panel.add(details, BorderLayout.EAST);
	}
}
