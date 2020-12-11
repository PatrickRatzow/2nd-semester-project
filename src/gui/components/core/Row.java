package gui.components.core;

import util.Colors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class Row extends JPanel {
    protected JLabel title;
    protected JButton mainButton;
    protected JPanel rightContainer;
    protected JPanel leftContainer;

    public Row() {
        this("Unnamed Row", "Button", false);
    }

    public Row(boolean even) {
        this("Unnamed Row", "Button", even);
    }

    public Row(String titleText, String buttonText, boolean even) {
        setLayout(new BorderLayout(0, 0));
        setMaximumSize(new Dimension(32746, 50));
        if (even) {
            setBackground(Colors.PRIMARY.getColor());
        }

        rightContainer = new JPanel();
        rightContainer.setBorder(new EmptyBorder(5, 5, 5, 5));
        rightContainer.setOpaque(false);
        add(rightContainer, BorderLayout.EAST);
        rightContainer.setLayout(new BorderLayout(0, 0));

        mainButton = new JButton(buttonText);
        mainButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rightContainer.add(mainButton);

        leftContainer = new JPanel();
        leftContainer.setOpaque(false);
        add(leftContainer, BorderLayout.WEST);
        leftContainer.setLayout(new BorderLayout(0, 0));

        title = new JLabel(titleText);
        title.setInheritsPopupMenu(false);
        title.setBorder(new EmptyBorder(5, 5, 5, 5));
        title.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        leftContainer.add(title);
    }

    public void setTitleText(String text) {
        title.setText(text);
    }

    public void setButtonText(String text) {
        mainButton.setText(text);
    }

    public void removeAllActionListeners() {
        for (ActionListener al : mainButton.getActionListeners()) {
            mainButton.removeActionListener(al);
        }
    }

    public void addActionListener(ActionListener listener) {
        mainButton.addActionListener(listener);
    }
}
