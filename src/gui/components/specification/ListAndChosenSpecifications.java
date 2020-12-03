package gui.components.specification;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import gui.components.core.TabPanel;
import net.miginfocom.swing.MigLayout;

public class ListAndChosenSpecifications extends JPanel{
	
	private JTextField txtChosenSpecifications;
	private JTextField txtListSpecifications;
	private JPanel specificationsList;
	private TabPanel panelManager;
	
	public ListAndChosenSpecifications(TabPanel panelManger) {
		
		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("Button.shadow"));
		panel.setLayout(new MigLayout("", "[::250px,grow][grow]", "[][grow]"));
		
		txtListSpecifications = new JTextField();
		txtListSpecifications.setBackground(Color.GRAY);
		txtListSpecifications.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtListSpecifications.setHorizontalAlignment(SwingConstants.CENTER);
		txtListSpecifications.setText("Liste af specifikationer");
		panel.add(txtListSpecifications, "cell 0 0,grow");
		
		txtChosenSpecifications = new JTextField();
		txtChosenSpecifications.setBackground(Color.GRAY);
		txtChosenSpecifications.setFont(new Font("Tohoma", Font.BOLD, 15));
		txtChosenSpecifications.setHorizontalAlignment(SwingConstants.CENTER);
		txtChosenSpecifications.setText("Valgte specifikationer");
		panel.add(txtChosenSpecifications, "cell 1 0,grow");
		
		JScrollPane listScrollPane = new JScrollPane();
		panel.add(listScrollPane, "cell 0 1,grow");
		
		specificationsList = new JPanel();
		listScrollPane.setViewportView(specificationsList);
		specificationsList.setBackground(Color.GRAY);
		specificationsList.setLayout(new BoxLayout(specificationsList, BoxLayout.Y_AXIS));
		
		JScrollPane chosenScrollPane = new JScrollPane();
		panel.add(chosenScrollPane, "cell 1 1,grow");
		
		JPanel chosenSpecifications = new JPanel();
		chosenScrollPane.setViewportView(chosenSpecifications);
		chosenSpecifications.setBackground(Color.GRAY);
		chosenSpecifications.setLayout(new BoxLayout(chosenSpecifications, BoxLayout.Y_AXIS));
	}
}