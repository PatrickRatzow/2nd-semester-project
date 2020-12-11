package util;

import java.awt.*;

public enum Colors {
    PRIMARY(new Color(205, 205, 205)),
    SECONDARY(new Color(225, 225, 225)),
    GREEN(new Color(152, 251, 152)),
    RED(new Color(252, 92, 101));

    private final Color color;

    Colors(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}
