package gui;

import com.formdev.flatlaf.FlatLightLaf;
import gui.components.core.PanelManager;
import gui.tabs.Tab;
import gui.tabs.customer.Customers;
import gui.tabs.product_finder.ProductFinder;
import gui.tabs.project.Projects;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class Frame extends JFrame {
    private final JTabbedPane tabbedPane;
    private static Frame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(new FlatLightLaf());
                    frame = new Frame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static Frame getInstance() {
        return frame;
    }

    public Frame() {
        setTitle("Kølby Tømrer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 600);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(0, 1, 0, 0));

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane);

        try {
            createTab("Projekter", Projects.class);
            createTab("Kunder", Customers.class);
            createTab("Billigste produkter", ProductFinder.class);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void createTab(String name, Class<? extends Tab> tabClass) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        PanelManager manager = new PanelManager();
        Tab tab = tabClass.getDeclaredConstructor(PanelManager.class).newInstance(manager);

        manager.setActive("main", () -> tab);
        tabbedPane.addTab(name, manager);
    }
}
