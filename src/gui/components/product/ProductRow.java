package gui.components.product;

import gui.components.core.Row;
import gui.util.Colors;

import javax.swing.*;
import java.awt.*;

public class ProductRow extends Row {
	private JLabel priceLabel;
	
	public ProductRow(String name, String price, boolean even) {
		super(name, "", even);
		
		// TODO: Dumb down row to not include button by default so we don't have to do this here
		remove(mainButton);
		
		priceLabel = new JLabel(price);
		priceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rightContainer.add(priceLabel);
        
        if (even) {
        	setBackground(Colors.SECONDARY.getColor());
        }
	}
}