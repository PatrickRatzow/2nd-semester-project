package gui.tabs.product_finder;

import controller.OrderController;
import gui.components.core.PanelManager;
import gui.components.core.TitleBar;
import gui.components.product.ProductsBox;

import javax.swing.*;
import java.awt.*;

public class ProductFinderProducts extends JPanel {
	private PanelManager panelManager;
	private OrderController orderController;
	private String previousId;
	
	public ProductFinderProducts(PanelManager panelManager, OrderController orderController) {
		this.panelManager = panelManager;
		this.orderController = orderController;
		previousId = panelManager.getCurrentId();
		
		setLayout(new BorderLayout(0, 0));
		
		TitleBar titleBar = new TitleBar();
		titleBar.setTitle("Resultat");
		titleBar.setButtonName("Gå Tilbage");
		titleBar.addActionListener(l -> goBack());
		add(titleBar, BorderLayout.NORTH);
		
		ProductsBox box = new ProductsBox(orderController.getOrderLines(), orderController.getPrice());
		add(box, BorderLayout.CENTER);
	}
	
	private void goBack() {
		panelManager.setActiveAndRemoveCurrent(previousId);
	}
}
