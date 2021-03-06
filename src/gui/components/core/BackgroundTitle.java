package gui.components.core;

import util.Colors;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BackgroundTitle extends JPanel {
    private final JLabel titleLabel;
    private final JPanel container;
    private final JPanel titleContainer;

    public BackgroundTitle() {
        this("Text");
    }

    public BackgroundTitle(String titleText) {
        setLayout(new BorderLayout(0, 0));
        setBackground(Colors.SECONDARY.getColor());

        titleContainer = new JPanel();
        titleContainer.setOpaque(false);
        titleContainer.setBorder(new EmptyBorder(5, 5, 0, 5));
        add(titleContainer, BorderLayout.NORTH);
        titleContainer.setLayout(new BoxLayout(titleContainer, BoxLayout.Y_AXIS));

        titleLabel = new JLabel(titleText);
        titleLabel.setBorder(new EmptyBorder(0, 0, 5, 0));
        titleLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleContainer.add(titleLabel);

        JPanel line = new JPanel();
        line.setBorder(null);
        line.setMaximumSize(new Dimension(32767, 1));
        line.setBackground(Color.BLACK);
        titleContainer.add(line);

        container = new JPanel();
        container.setOpaque(false);
        container.setBorder(new EmptyBorder(0, 5, 5, 5));
        container.setLayout(new CardLayout(0, 0));
        add(container, BorderLayout.CENTER);
    }
    
    public void setTitleBorder(Border border) {
        titleContainer.setBorder(border);
    }
    
    public void setContainerBorder(Border border) {
        container.setBorder(border);
    }

    public void setTitle(String text) {
        titleLabel.setText(text);
    }

    @Override
    public Component add(Component component) {
        container.add(component);

        return component;
    }
}
