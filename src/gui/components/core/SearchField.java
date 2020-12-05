package gui.components.core;

import javax.swing.*;
import java.awt.*;

public class SearchField extends JTextField {
	private String placeholder;
	
	public SearchField() {
		setForeground(Color.BLACK);
	}
	
	public void setPlaceholder(String text) {
		placeholder = text;
	}
	
	public String getPlaceholder() {
		return placeholder;
	}
	
	// Slightly modified version of https://stackoverflow.com/a/16229082
    @Override
    protected void paintComponent(final Graphics pG) {
        super.paintComponent(pG);

        if (placeholder == null || placeholder.length() == 0 || getText().length() > 0) {
            return;
        }

        final Graphics2D g = (Graphics2D) pG;
        g.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(getDisabledTextColor());
        g.drawString(placeholder, getInsets().left, getSize().height / 2 + 
        		pG.getFontMetrics().getMaxAscent() / 2);
    }
}
