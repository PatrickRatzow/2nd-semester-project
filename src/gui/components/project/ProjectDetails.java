package gui.components.project;

import controller.EmployeeController;
import controller.ProjectController;
import gui.Frame;
import gui.components.core.BackgroundTitle;
import gui.components.core.PanelManager;
import gui.components.core.PlaceholderTextField;
import gui.util.Colors;
import model.Employee;
import model.ProjectStatus;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ProjectDetails extends BackgroundTitle {
	private final JPanel leftContainer;
	private final JPanel rightContainer;
	private final PlaceholderTextField name;
	private final PlaceholderTextField price;
	private final PlaceholderTextField estimatedHours;
	private final JComboBox<Employee> employeeBox;
	private final JComboBox<ProjectStatus> statusBox;
	private final ProjectController projectController;
	private final boolean isCreatingProject;

	public ProjectDetails(PanelManager panelManager, ProjectController projectController) {
		isCreatingProject = projectController.getName() == null;
		this.projectController = projectController;
		EmployeeController employeeController = new EmployeeController();
		
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
		
		JPanel bottomContainer = new JPanel();
		bottomContainer.setOpaque(false);
		panel.add(bottomContainer, BorderLayout.SOUTH);
		bottomContainer.setLayout(new BorderLayout(0, 0));
		
		JPanel rightAlign = new JPanel();
		rightAlign.setOpaque(false);
		bottomContainer.add(rightAlign, BorderLayout.EAST);
		
		JButton cancelBtn = new JButton("Annuller");
		cancelBtn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		cancelBtn.setBackground(Colors.RED.getColor());
		cancelBtn.setVisible(isCreatingProject);
		cancelBtn.addActionListener(e -> panelManager.setActiveAndRemoveEverythingElse("main"));
		rightAlign.add(cancelBtn);
		
		JButton createBtn = new JButton((isCreatingProject ? "Opret" : "Opdater") + " & Gem");
		createBtn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		createBtn.setBackground(Colors.GREEN.getColor());
		projectController.addSaveListener(p -> {
			String text = p.getName() + " er blevet " + (isCreatingProject ? "oprettet" : "opdateret");
			
			Frame.createSuccessPopup(text);
			
			panelManager.setActiveAndRemoveEverythingElse("main");
			projectController.getAll();
		});
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
		
		statusBox = new JComboBox<ProjectStatus>(ProjectStatus.values());
		createRow("Status", statusBox);
		createSpacer();
		
		createBtn.addActionListener(l -> {
			try {
				String name = this.name.getText();
				int price = Integer.parseInt(this.price.getText());
				int estimatedHours = Integer.parseInt(this.estimatedHours.getText());
				Employee employee = (Employee) employeeBox.getSelectedItem();
				ProjectStatus status = (ProjectStatus) statusBox.getSelectedItem();
				
				projectController.setName(name);
				projectController.setPrice(price);
				projectController.setEstimatedHours(estimatedHours);
				projectController.setLeadEmployee(employee);
				projectController.setStatus(status);
				projectController.save();
			} catch (NumberFormatException e) {
				Frame.createErrorPopup(new Exception("Det er ikke numre i pris/estimeret timer felterne!"));
			} catch (Exception e) {
				Frame.createErrorPopup(e);
			}
		});
		employeeController.addFindListener(employees -> {
			for (Employee employee : employees) {
				employeeBox.addItem(employee);
			}
			
			// In case we're viewing an existing project
			if (!isCreatingProject) {
				employeeBox.setSelectedItem(projectController.getLeadEmployee());
			}
		});
		employeeController.getDirectors();
		
		setProject();
	}
	
	public void setProject() {
		if (isCreatingProject) return;
		
		name.setText(projectController.getName());
		price.setText(projectController.getPrice().getNumberString());
		estimatedHours.setText(String.valueOf(projectController.getEstimatedHours()));
		employeeBox.setSelectedItem(projectController.getLeadEmployee());
		statusBox.setSelectedItem(projectController.getStatus());
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
