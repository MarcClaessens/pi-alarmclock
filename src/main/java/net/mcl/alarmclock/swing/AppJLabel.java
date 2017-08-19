package net.mcl.alarmclock.swing;

import javax.swing.JLabel;

import net.mcl.alarmclock.AppFonts;

/**
 * JLabel that takes the background color of the application. Registers a
 * right-click listener to quit.
 */

public class AppJLabel extends JLabel {
    public AppJLabel(String label, AppFonts style) {
        super(label);
        style.applyStyle(this);
    }

    public AppJLabel(AppFonts style) {
        style.applyStyle(this);
    }

}
