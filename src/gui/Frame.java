package gui;

import entity.Specification;
import entity.specifications.Window;
import gui.components.specification.SpecificationTab;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Frame extends JFrame {
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		
	    JComponent projects = new Projects();
	    Specification window = new Window();
	    JComponent s = new SpecificationTab(window);
	    
	    tabbedPane.addTab("SpecificationTab", s);
	    JComponent customers = new JPanel(false);
	    tabbedPane.addTab("Kunder", customers);
	    JComponent cheapestProducts = new JPanel(false);
	    tabbedPane.addTab("Billigste produkter", cheapestProducts);
	}

}
