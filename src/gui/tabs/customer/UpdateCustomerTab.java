package gui.tabs.customer;

import controller.CustomerController;
import entity.Customer;
import gui.components.core.PanelManager;
import gui.components.core.TitleBar;
import gui.components.customer.UpdateCustomer;

import javax.swing.*;
import java.awt.*;

public class UpdateCustomerTab extends JComponent {
    public UpdateCustomerTab(PanelManager panelManager, CustomerController customerController, String title, String buttonName, Customer customer) {
        setLayout(new BorderLayout(0, 0));

        String previousId = panelManager.getCurrentId();

        TitleBar titleBar = new TitleBar();
        titleBar.setTitle(title);
        titleBar.setButtonName(buttonName);
        titleBar.addActionListener(e -> panelManager.setActiveAndRemoveCurrent(previousId));
        add(titleBar, BorderLayout.NORTH);

        UpdateCustomer updateCustomer = new UpdateCustomer(customerController, customer);
        add(updateCustomer, BorderLayout.CENTER);
    }
}