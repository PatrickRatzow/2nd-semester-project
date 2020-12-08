package gui.tabs.product_finder;

import controller.OrderController;
import gui.components.core.PanelManager;
import gui.components.core.TitleBar;
import gui.components.product.ProductsBox;

import javax.swing.*;
import java.awt.*;

public class ProductFinderResult extends JPanel {
	public ProductFinderResult(PanelManager panelManager, OrderController orderController) {
		String previousId = panelManager.getCurrentId();
		setLayout(new BorderLayout(0, 0));
		
		TitleBar titleBar = new TitleBar();
		titleBar.setTitle("Resultat");
		titleBar.setButtonName("Gå Tilbage");
		titleBar.addActionListener(l -> panelManager.setActiveAndRemoveCurrent(previousId));
		add(titleBar, BorderLayout.NORTH);

		ProductsBox box = new ProductsBox(orderController);
		add(box, BorderLayout.CENTER);
	}
}
