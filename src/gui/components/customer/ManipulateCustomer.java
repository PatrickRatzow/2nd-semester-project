package gui.components.customer;

import controller.CustomerController;
import gui.util.Colors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public abstract class ManipulateCustomer extends JPanel {
    protected ManipulateCustomerColumn firstName;
    protected ManipulateCustomerColumn address;
    protected ManipulateCustomerColumn city;
    protected ManipulateCustomerColumn phoneNumber;
    protected ManipulateCustomerColumn lastName;
    protected ManipulateCustomerColumn addressNumber;
    protected ManipulateCustomerColumn zipCode;
    protected ManipulateCustomerColumn email;

    protected CustomerController customerController;
    protected JButton btnAdd;

    public ManipulateCustomer(CustomerController customerController) {
        setOpaque(false);
        setLayout(new BorderLayout(0, 0));

        this.customerController = customerController;

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonContainer.setOpaque(false);
        add(buttonContainer, BorderLayout.SOUTH);

        btnAdd = new JButton();
        btnAdd.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
        btnAdd.setBackground(Colors.GREEN.getColor());
        buttonContainer.add(btnAdd);

        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setBorder(new EmptyBorder(0, 0, 20, 0));
        container.setLayout(new GridLayout(0, 2, 30, 0));
        add(container, BorderLayout.CENTER);

        JPanel leftColumn = new JPanel();
        leftColumn.setOpaque(false);
        leftColumn.setLayout(new BoxLayout(leftColumn, BoxLayout.Y_AXIS));
        container.add(leftColumn);

        Dimension spacing = new Dimension(0, 5);
        firstName = new ManipulateCustomerColumn("Fornavn");
        leftColumn.add(firstName);

        Component firstNameSpacer = Box.createRigidArea(spacing);
        leftColumn.add(firstNameSpacer);

        address = new ManipulateCustomerColumn("Adresse");
        leftColumn.add(address);

        Component addressSpacer = Box.createRigidArea(spacing);
        leftColumn.add(addressSpacer);

        city = new ManipulateCustomerColumn("By");
        leftColumn.add(city);

        Component citySpacer = Box.createRigidArea(spacing);
        leftColumn.add(citySpacer);

        phoneNumber = new ManipulateCustomerColumn("Telefonnummer");
        leftColumn.add(phoneNumber);

        JPanel rightColumn = new JPanel();
        rightColumn.setOpaque(false);
        rightColumn.setLayout(new BoxLayout(rightColumn, BoxLayout.Y_AXIS));
        container.add(rightColumn);

        lastName = new ManipulateCustomerColumn("Efternavn");
        rightColumn.add(lastName);

        Component lastNameSpacer = Box.createRigidArea(spacing);
        rightColumn.add(lastNameSpacer);

        addressNumber = new ManipulateCustomerColumn("Adresse nummer");
        rightColumn.add(addressNumber);

        Component addressNumberSpacer = Box.createRigidArea(spacing);
        rightColumn.add(addressNumberSpacer);

        zipCode = new ManipulateCustomerColumn("Postnummer");
        rightColumn.add(zipCode);

        Component zipCodeSpacer = Box.createRigidArea(spacing);
        rightColumn.add(zipCodeSpacer);

        email = new ManipulateCustomerColumn("Email");
        rightColumn.add(email);

        btnAdd.addActionListener(l -> onSave());
    }

    public abstract void onSave();
}
