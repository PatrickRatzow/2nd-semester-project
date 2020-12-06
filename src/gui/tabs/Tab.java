package gui.tabs;

import gui.components.core.PanelManager;

import javax.swing.*;

public abstract class Tab extends JComponent  {
    protected PanelManager panelManager;

    public Tab(PanelManager panelManager) {
        this.panelManager = panelManager;
    }
}
