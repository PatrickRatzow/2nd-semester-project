package gui.components.project;

import controller.EmployeeController;
import gui.components.core.BackgroundTitle;
import gui.components.core.PlaceholderTextField;
import gui.util.Colors;
import model.Employee;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ProjectDetails extends BackgroundTitle {
	private JPanel leftContainer;
	private JPanel rightContainer;
	private PlaceholderTextField name;
	private PlaceholderTextField price;
	private PlaceholderTextField estimatedHours;
	private JComboBox<Employee> employeeBox;
	private EmployeeController employeeController;
	private JPanel bottomContainer;
	private JPanel rightAlign;
	private JButton createBtn;
	private JButton cancelBtn;
	
	public ProjectDetails() {
		employeeController = new EmployeeController();
		
		setTitle("Projekt detajler");
		Dimension dimension = getPreferredSize();
		dimension.width = 250;
		setPreferredSize(dimension);
		setBackground(Colors.PRIMARY.getColor());
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout(0, 0));
		add(panel);
		
		leftContainer = new JPanel();
		leftContainer.setBorder(new EmptyBorder(0, 5, 0, 5));
		leftContainer.setOpaque(false);
		leftContainer.setLayout(new BoxLayout(leftContainer, BoxLayout.Y_AXIS));
		panel.add(leftContainer, BorderLayout.WEST);
		
		rightContainer = new JPanel();
		rightContainer.setOpaque(false);
		rightContainer.setLayout(new BoxLayout(rightContainer, BoxLayout.Y_AXIS));
		panel.add(rightContainer, BorderLayout.CENTER);
		
		name = new PlaceholderTextField();
		name.setPlaceholder("Projekt navn");
		name.setColumns(10);
		createRow("Navn", name);
		
		bottomContainer = new JPanel();
		bottomContainer.setOpaque(false);
		panel.add(bottomContainer, BorderLayout.SOUTH);
		bottomContainer.setLayout(new BorderLayout(0, 0));
		
		rightAlign = new JPanel();
		rightAlign.setOpaque(false);
		bottomContainer.add(rightAlign, BorderLayout.EAST);
		
		cancelBtn = new JButton("Annuller");
		cancelBtn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		cancelBtn.setBackground(Colors.RED.getColor());
		rightAlign.add(cancelBtn);
		
		createBtn = new JButton("Opret & Gem");
		createBtn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		createBtn.setBackground(Colors.GREEN.getColor());
		rightAlign.add(createBtn);
		
		createSpacer();
		
		price = new PlaceholderTextField();
		price.setPlaceholder("Pris");
		price.setColumns(10);
		createRow("Pris", price);
		createSpacer();
		
		estimatedHours = new PlaceholderTextField();
		estimatedHours.setPlaceholder("Estimeret timetal");
		estimatedHours.setColumns(10);
		createRow("Timer", estimatedHours);
		createSpacer();
		
		employeeBox = new JComboBox<Employee>();
		createRow("Ansvarlig", employeeBox);
		createSpacer();
		
		employeeController.addFindListener(employees -> {
			for (Employee employee : employees) {
				employeeBox.addItem(employee);
			}
		});
		employeeController.getDirectors();
	}
	
	private void createSpacer() {
		leftContainer.add(Box.createRigidArea(new Dimension(0, 5)));
		rightContainer.add(Box.createRigidArea(new Dimension(0, 5)));
	}
	
	private void createRow(String title, JComponent component) {		
		JLabel name = new JLabel(title);
		name.setAlignmentX(Component.RIGHT_ALIGNMENT);
		name.setBorder(new EmptyBorder(4, 0, 6, 0));
		Dimension dimension = name.getMinimumSize();
		dimension.height = 30;
		name.setMinimumSize(dimension);
		name.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		leftContainer.add(name, BorderLayout.WEST);

		component.setMaximumSize(new Dimension(20000, 30));
		rightContainer.add(component);
	}
}
