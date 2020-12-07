package gui.tabs.product_finder;

import gui.components.core.PanelManager;
import gui.components.core.TitleBar;
import gui.components.specifications.SpecificationsTab;
import gui.tabs.Tab;

import javax.swing.*;
import java.awt.*;

public class ProductFinder extends Tab {
    public ProductFinder(PanelManager panelManager) {
        super(panelManager);

        setLayout(new BorderLayout(0, 0));

        TitleBar titleBar = new TitleBar();
        titleBar.setTitle("Billigste produkter");
        titleBar.hideButton();
        add(titleBar, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane();
        add(scrollPane, BorderLayout.CENTER);
        
        SpecificationsTab specifications = new SpecificationsTab(panelManager);
        specifications.addSaveListener(orderController -> 
        	panelManager.setActive("products", () -> 
        		new ProductFinderProducts(panelManager, orderController)
        	)
    	);
        scrollPane.setViewportView(specifications);
    }
}
