package gui.tabs.customer;

import controller.CustomerController;
import gui.Frame;
import gui.components.core.PanelManager;
import gui.components.core.Row;
import gui.components.core.TitleBar;
import gui.tabs.Tab;
import model.Customer;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Customers extends Tab {
    private final CustomerController customerController;
    private final JPanel container;
    private String currentId;
    private final Map<Customer, Row> rows;

    public Customers(PanelManager panelManager) {
        super(panelManager);

        rows = new HashMap<>();
        customerController = new CustomerController();

        this.panelManager = panelManager;
        setLayout(new BorderLayout(0, 0));

        TitleBar titleBar = new TitleBar();
        titleBar.setTitle("Kunder");
        titleBar.setButtonName("Opret Kunde");
        titleBar.addActionListener(e -> {
            currentId = panelManager.getCurrentId();

            panelManager.setActive("create_customer", () ->
                    new CreateCustomerTab(panelManager, customerController, "Opret Kunde", "Gå Tilbage"));
        });

        JTextField searchBar = titleBar.createSearchBar("Kunde telefon/email");
        searchBar.addActionListener(e -> {
            String text = searchBar.getText();
            if (text.isEmpty()) {
                customerController.getAll();
            } else {
                customerController.getSearch(text);
            }
        });
        add(titleBar, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane();
        add(scrollPane, BorderLayout.CENTER);

        container = new JPanel();
        scrollPane.setViewportView(container);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        customerController.addFindListener(customers -> {
            container.removeAll();
            rows.clear();
            for (Customer customer : customers) {
                createRow(customer);
            }
            container.repaint();
        });
        customerController.addSaveListener(customer -> {
            Row row = rows.get(customer);
            if (row != null) {
                row.setTitleText(customer.getFirstName() + " " + customer.getLastName() +
                        " (tlf. " + customer.getPhoneNumber() + ")");
            } else {
                createRow(customer);
            }

            panelManager.setActiveAndRemoveCurrent(currentId);
        });
        customerController.addErrorListener(Frame::createErrorPopup);

        customerController.getAll();
    }

    private void addRowListener(Customer customer, Row row) {
        row.addActionListener(e -> {
            currentId = panelManager.getCurrentId();

            panelManager.setActive("update_customer", () -> {
                customerController.setCustomer(customer);

                return new UpdateCustomerTab(panelManager, customerController,
                        "Opdater Kunde", "Gå Tilbage", customer);
            });
        });
    }

    private void createRow(Customer customer) {
        if (rows.get(customer) != null) return;

        boolean even = (rows.size() + 1) % 2 == 0;
        Row row = new Row(even);
        row.setTitleText(customer.getFirstName() + " " + customer.getLastName() + " (tlf. " + customer.getPhoneNumber() + ")");
        row.setButtonText("Åben");
        addRowListener(customer, row);

        rows.put(customer, row);
        container.add(row);
    }
}
