package marcclaessens.alarmclock.swing;

import javax.swing.JLabel;

import marcclaessens.alarmclock.AppColor;
import marcclaessens.alarmclock.AppFonts;
import marcclaessens.alarmclock.ColorChangeListener;

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
