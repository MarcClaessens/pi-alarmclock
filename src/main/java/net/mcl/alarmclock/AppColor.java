package net.mcl.alarmclock;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public enum AppColor {

	FOREGROUND(Color.RED), BACKGROUND(Color.BLACK);

	private static Set<ColorChangeListener> listeners = new HashSet<>();

	private Color color;

	private AppColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void changeDefaultColor(Color color) {
		this.color = color;
		for (ColorChangeListener l : listeners) {
			l.changeColor(this);
		}
	}

	public static void registerListener(ColorChangeListener l) {
		listeners.add(l);
	}
}
