package gui.tabs.project;

import controller.CustomerController;
import controller.ProjectController;
import gui.components.core.BackgroundTitle;
import gui.components.core.PanelManager;
import gui.components.core.PlaceholderTextField;
import gui.components.core.TitleBar;
import gui.components.customer.CustomerInformationBox;
import model.Customer;
import util.Colors;

import javax.swing.*;
import java.awt.*;

import static gui.Frame.createErrorPopup;

public class ProjectFindOrCreateCustomer extends JPanel {
    private final PlaceholderTextField searchTextField;
    private JComponent resultComponent;
    private final JButton btnAddCustomer;
    private final JPanel resultContainer;
    private Customer customer;

    public ProjectFindOrCreateCustomer(PanelManager panelManager, ProjectController projectController) {
        CustomerController customerController = new CustomerController();
        String previousId = panelManager.getCurrentId();
        setLayout(new BorderLayout(0, 0));

        TitleBar titleBar = new TitleBar();
        titleBar.setTitle("Opret Projekt");
        titleBar.setButtonName("Gå tilbage");
        titleBar.addActionListener(l -> panelManager.setActiveAndRemoveCurrent(previousId));
        add(titleBar, BorderLayout.NORTH);

        BackgroundTitle backgroundTitle = new BackgroundTitle("Kunde (1/3)");
        add(backgroundTitle, BorderLayout.CENTER);
    
        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new BorderLayout(0, 0));
        backgroundTitle.add(container);

        JPanel topContainer = new JPanel();
        topContainer.setOpaque(false);
        container.add(topContainer, BorderLayout.NORTH);

        JPanel bottomContainer = new JPanel();
        bottomContainer.setOpaque(false);
        container.add(bottomContainer, BorderLayout.SOUTH);

        resultContainer = new JPanel();
        resultContainer.setOpaque(false);
        container.add(resultContainer, BorderLayout.CENTER);
        topContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        searchTextField = new PlaceholderTextField();
        searchTextField.setColumns(12);
        searchTextField.setPlaceholder("Telefonnummer/email");
        topContainer.add(searchTextField);

        btnAddCustomer = new JButton("Vælg Kunde");
        btnAddCustomer.setBackground(Colors.GREEN.getColor());
        btnAddCustomer.setVisible(false);
        btnAddCustomer.addActionListener(e -> {
            projectController.setCustomer(customer);

            panelManager.setActive("specifications",
                    () -> new ProjectSpecificationsTab(panelManager, projectController));
        });
        bottomContainer.add(btnAddCustomer);

        JButton btnSearch = new JButton("Søg");
        btnSearch.addActionListener(e -> customerController.getSearch(searchTextField.getText()));
        topContainer.add(btnSearch);

        JButton btnCreate = new JButton("Opret kunde");
        btnCreate.addActionListener(l -> {
            panelManager.setActive("create_customer", () ->
                    new ProjectCreateCustomerTab(panelManager, customerController));
        });
        topContainer.add(btnCreate);

        customerController.addFindListener(customers -> {
            if (resultComponent != null) {
                resultContainer.remove(resultComponent);
            }
            if (!customers.isEmpty()) {
                createCustomerDisplay(customers.get(0));
            } else {
                createNoResultDisplay();
            }
            revalidate();
            repaint();
        });
        customerController.addSaveListener(customer -> {
            projectController.setCustomer(customer);

            panelManager.setActive("specifications",
                    () -> new ProjectSpecificationsTab(panelManager, projectController));
            panelManager.removePanel("create_customer");
        });
    }

    private void createCustomerDisplay(Customer customer) {
        resultComponent = new CustomerInformationBox(customer);
        Dimension dimension = resultComponent.getPreferredSize();
        dimension.width = 400;
        resultComponent.setPreferredSize(dimension);
        resultContainer.add(resultComponent);

        this.customer = customer;

        btnAddCustomer.setVisible(true);
    }

    private void createNoResultDisplay() {
    	String text = searchTextField.getText();
    	if (text.isEmpty()) {
    		createErrorPopup(new Exception("Indtast et telefonnummer eller email først!"));
    	} else {
    		createErrorPopup(new Exception(text + " findes ikke"));
    	}
        
        this.customer = null;
        
        btnAddCustomer.setVisible(false);
    }
}