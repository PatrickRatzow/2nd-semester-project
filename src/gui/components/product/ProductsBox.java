package gui.components.product;

import controller.OrderController;
import gui.components.core.BackgroundTitle;
import model.OrderLine;
import model.Price;
import model.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Collection;

public class ProductsBox extends BackgroundTitle {
	private BackgroundTitle priceContainer;
	private JPanel container;
	
	public ProductsBox(OrderController orderController) {
		setTitle("Produkter");
		setBorder(new EmptyBorder(0, 0, 0, 0));
		
		priceContainer = new BackgroundTitle();
		priceContainer.setTitleBorder(new EmptyBorder(0, 0, 0, 0));
		priceContainer.setContainerBorder(new EmptyBorder(0, 0, 0, 0));
		add(priceContainer);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		priceContainer.add(scrollPane);
		
		container = new JPanel();
		container.setOpaque(false);
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		scrollPane.setViewportView(container);
		
		if (orderController.hasOrder()) {
			Collection<OrderLine> orderLines = orderController.getOrderLines();
			Price price = orderController.getPrice();
			
			setOrderLines(orderLines);
			setPrice(price);
		} else {
			setPrice(new Price(0));
		}
	}
	
	public void setPriceBackground(Color color) {
		priceContainer.setBackground(color);
	}
	
	public void setPrice(Price price) {
		priceContainer.setTitle(price.toString());
	}
	
	public void setOrderLines(Collection<OrderLine> orderLines) {
		container.removeAll();
		
		int i = 0;
		for (OrderLine orderLine : orderLines) {
			i++;
			
			boolean even = i % 2 == 0;
			Product product = orderLine.getProduct();
			int quantity = orderLine.getQuantity();
			String priceDisplay = product.getPrice().toString();
			if (quantity > 1) {
				priceDisplay = quantity + "x " + priceDisplay;
			}
			ProductRow row = new ProductRow(product.getName(), orderLine.getDisplayName(), priceDisplay, even);
				
			container.add(row);
		}
		
		container.revalidate();
		container.repaint();
	}
}
