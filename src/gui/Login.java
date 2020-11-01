package gui;

import controller.EmployeeController;
import controller.WrongPasswordException;
import database.DataAccessException;
import model.Employee;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class Login {
	private JFrame frame;
	private JTextField usernameInput;
	private JPasswordField passwordInput;
	private JPanel errorContainer;
	private JLabel error;
	final private EmployeeController employeeController = new EmployeeController();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					window.frame.setLocation(dim.width / 2 - window.frame.getSize().width / 2,
							dim.height / 2 - window.frame.getSize().height / 2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		init();
	}

	private void init() {
		frame = new JFrame();
		frame.setBounds(100, 100, 350, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
		
		JPanel titleContainer = new JPanel();
		titleContainer.setMaximumSize(new Dimension(32767, 40));
		FlowLayout fl_titleContainer = (FlowLayout) titleContainer.getLayout();
		fl_titleContainer.setHgap(0);
		frame.getContentPane().add(titleContainer);
		
		JLabel title = new JLabel("Please login");
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleContainer.add(title);
		
		errorContainer = new JPanel();
		errorContainer.setMaximumSize(new Dimension(32767, 25));
		errorContainer.setVisible(false);
		frame.getContentPane().add(errorContainer);
		
		error = new JLabel("Error!");
		error.setFont(new Font("Tahoma", Font.PLAIN, 15));
		error.setForeground(Color.RED);
		errorContainer.add(error);
		
		JPanel mainContainer = new JPanel();
		frame.getContentPane().add(mainContainer);
		mainContainer.setLayout(new GridLayout(0, 1, 0, 5));
		mainContainer.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JSplitPane username = new JSplitPane();
		username.setMinimumSize(new Dimension(250, 40));
		mainContainer.add(username);
		
		JLabel usernameLabel = new JLabel("Username");
		username.setLeftComponent(usernameLabel);
		
		usernameInput = new JTextField();
		username.setRightComponent(usernameInput);
		usernameInput.setText("Type your username");
		usernameInput.setForeground(Color.GRAY);
		usernameInput.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (usernameInput.getText().equals("Type your username")) {
					usernameInput.setText("");
					usernameInput.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (usernameInput.getText().isEmpty()) {
					usernameInput.setForeground(Color.GRAY);
					usernameInput.setText("Type your username");
				}
			}
		});
		usernameInput.setMinimumSize(new Dimension(250, 0));
		
		JSplitPane password = new JSplitPane();
		password.setMinimumSize(new Dimension(250, 40));
		mainContainer.add(password);
		
		JLabel passwordLabel = new JLabel("Password");
		password.setLeftComponent(passwordLabel);
		
		passwordInput = new JPasswordField();
		password.setRightComponent(passwordInput);
		passwordInput.setMinimumSize(new Dimension(250, 0));
		
		JPanel loginContainer = new JPanel();
		FlowLayout flowLayout = (FlowLayout) loginContainer.getLayout();
		flowLayout.setHgap(0);
		flowLayout.setVgap(0);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		mainContainer.add(loginContainer);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usernameText = usernameInput.getText();
				String passwordText = String.valueOf(passwordInput.getPassword());

				try {
					Employee employee = employeeController.findByUsernameAndPassword(usernameText, passwordText);
					
					/*
					 * TODO: Do something with the employee. New UI? Remember to keep track of the employee
					 */
				} catch (DataAccessException dataAccessException) {
					setDisplayError("Couldn't find any employee with that username");
				} catch (WrongPasswordException wrongPasswordException) {
					setDisplayError("Wrong password");
				}
			}
		});
		loginContainer.add(loginButton);
	}

	private void setDisplayError(String displayError) {
		errorContainer.setVisible(true);
		error.setText(displayError);
	}
}
