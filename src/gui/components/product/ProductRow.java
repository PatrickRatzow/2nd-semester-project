package gui.components.product;

import util.Colors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ProductRow extends JPanel {
    public ProductRow(String productName, String displayName, String price, boolean even) {
        setLayout(new BorderLayout(0, 0));
        setMaximumSize(new Dimension(32746, 50));
        if (even) {
        	setBackground(Colors.SECONDARY.getColor());
        }

        JPanel rightContainer = new JPanel();
        rightContainer.setBorder(new EmptyBorder(5, 5, 5, 5));
        rightContainer.setOpaque(false);
        add(rightContainer, BorderLayout.EAST);
        rightContainer.setLayout(new BorderLayout(0, 0));

        JLabel label = new JLabel(price);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rightContainer.add(label);

        JPanel leftContainer = new JPanel();
        leftContainer.setOpaque(false);
        add(leftContainer, BorderLayout.WEST);
        leftContainer.setLayout(new BoxLayout(leftContainer, BoxLayout.Y_AXIS));

        JLabel displayNameLabel = new JLabel(displayName);
        displayNameLabel.setBorder(new EmptyBorder(5, 5, 0, 0));
        displayNameLabel.setForeground(Color.DARK_GRAY);
        displayNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        leftContainer.add(displayNameLabel);
        
        JLabel title = new JLabel(productName);
        title.setInheritsPopupMenu(false);
        title.setBorder(new EmptyBorder(0, 5, 5, 5));
        title.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        leftContainer.add(title);
    }
}