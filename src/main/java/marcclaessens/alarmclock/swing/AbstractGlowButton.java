package marcclaessens.alarmclock.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import marcclaessens.alarmclock.AppColor;
import marcclaessens.alarmclock.AppContext;
import marcclaessens.alarmclock.ColorChangeListener;

/**
 * Button with a glowing border. The color is defined in the CSS file.
 */
public abstract class AbstractGlowButton extends JButton implements ColorChangeListener {
	private static final long serialVersionUID = 1L;

	private final AppContext context;

	protected AbstractGlowButton(AppContext context) {
		super();

		this.setContentAreaFilled(false);

		this.context = context;
		setAlignmentX(CENTER_ALIGNMENT);
		setAlignmentY(CENTER_ALIGNMENT);
		changeColor(AppColor.FOREGROUND);
		changeColor(AppColor.BACKGROUND);
		addActionListener(this::clicked);

		changeColor(AppColor.FOREGROUND);
		changeColor(AppColor.BACKGROUND);

		setFocusPainted(false);
	}

	protected abstract void clicked(ActionEvent event);

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

	protected AppContext getContext() {
		return context;
	}

	@Override
	public void changeColor(AppColor color) {
		if (color == AppColor.FOREGROUND) {
			setForeground(color.getColor());
			CompoundBorder border1 = BorderFactory.createCompoundBorder(
					new LineBorder(color.getColor().darker().darker(), 3, true),
					new LineBorder(color.getColor().darker(), 2, true));
			CompoundBorder border2 = BorderFactory.createCompoundBorder(border1,
					new LineBorder(color.getColor(), 1, true));
			setBorder(border2);

		} else {
			setBackground(color.getColor());
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (getModel().isPressed()) {
			g.setColor(Color.yellow);
		} else if (getModel().isRollover()) {
			g.setColor(Color.green);
		} else {
			g.setColor(Color.blue);
		}
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}

	@Override
	public void setContentAreaFilled(boolean b) {
		// do nothing
	}
}
