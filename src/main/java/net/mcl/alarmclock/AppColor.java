package net.mcl.alarmclock;

import java.awt.Color;

public enum AppColor {

    FOREGROUND(Color.RED),
    BACKGROUND(Color.BLACK);

    private Color color;

    private AppColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void changeDefaultColor(Color color) {
        this.color = color;
    }
}
