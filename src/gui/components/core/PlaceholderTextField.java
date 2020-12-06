package gui.components.core;

import javax.swing.*;
import java.awt.*;

public class PlaceholderTextField extends JTextField {
	private String placeholder;
	
	public PlaceholderTextField() {
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
        int y = (int) (getSize().getHeight() / 2 + pG.getFontMetrics().getMaxAscent() / 2) - 1;
        // Hack to fix a bug where if y is even it's 1 px off
        if (y % 2 == 0) {
        	y--;
        }
        g.drawString(placeholder, getInsets().left, y);
    }
}
