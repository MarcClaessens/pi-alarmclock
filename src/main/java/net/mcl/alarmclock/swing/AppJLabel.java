package net.mcl.alarmclock.swing;

import javax.swing.JLabel;

import net.mcl.alarmclock.AppColor;
import net.mcl.alarmclock.AppFonts;
import net.mcl.alarmclock.ColorChangeListener;

/**
 * JLabel that takes the background color of the application. Registers a
 * right-click listener to quit.
 */

public class AppJLabel extends JLabel implements ColorChangeListener {
	private static final long serialVersionUID = 1L;

	public AppJLabel(String label, AppFonts style) {
		super(label);
		style.applyStyle(this);
	}

	public AppJLabel(AppFonts style) {
		style.applyStyle(this);
	}

	@Override
	public void changeColor(AppColor c) {
		// button is already registered as ColorChangeListener via its superclass
		if (c == AppColor.FOREGROUND) {
			setForeground(c.getColor());
		} else {
			setBackground(c.getColor());
		}
	}

}
