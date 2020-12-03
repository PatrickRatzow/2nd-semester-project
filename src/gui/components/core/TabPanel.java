package gui.components.core;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class TabPanel extends JPanel {
    private Map<String, JComponent> components = new HashMap<>();
    private String currentId;
    
    public TabPanel() {
        setLayout(new CardLayout(0, 0));
    }
    
    public void removePanel(String id) {
    	JComponent component = components.get(id);
    	if (component == null) {
    		return;
    	}
    	
    	remove(component);
    	components.remove(id);
    }
    
    public void setActive(String id, Callable<? extends JComponent> panelCreation) {
    	if (components.get(id) != null) {
    		removePanel(id);
    	}
    
        try {
			addPanel(id, panelCreation.call());
		} catch (Exception e) {
			e.printStackTrace();
		}

        setActive(id);
    }
    
    public String getCurrentId() {
    	return currentId;
    }
    
    private void addPanel(String id, JComponent component) {
        components.putIfAbsent(id, component);

        add(component, id);
    }

    public void setActive(String id) {
        if (components.get(id) == null) {
            return;
        }

        CardLayout cl = (CardLayout)getLayout();
        cl.show(this, id);
        currentId = id;
    }
}

