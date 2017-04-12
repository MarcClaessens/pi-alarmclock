package net.mcl.alarmclock;

import javafx.scene.Node;

/**
 * Enum of defined CSS classes.
 */
public enum CSS {
    CLOCK_FONT("clockfont"), AWESOME_FONT("awesomefont"), MATERIAL_DESIGN_FONT("materialdesignfont"), STANDARD_FONT(
            "stdfont"), GLOW("glow"), DEBUG_BORDER("debugborder");

    private String cssclass;

    private CSS(String cssclass) {
        this.cssclass = cssclass;
    }

    public void applyStyle(Node n) {
        n.getStyleClass().add(cssclass);
    }
}
