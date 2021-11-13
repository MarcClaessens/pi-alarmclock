package net.mcl.alarmclock.menu;

import java.awt.event.ActionEvent;

import net.mcl.alarmclock.AlarmClock;
import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.CharIcon;
import net.mcl.alarmclock.swing.AbstractIconGlowButton;

/**
 * Button to adjust the alarm time setting.
 */
class TimeAdjustButton extends AbstractIconGlowButton {
	private static final long serialVersionUID = 1L;
	private final int diff;
	private final boolean up;
	private final boolean hours;
	private final boolean doubleSize;

	public TimeAdjustButton(AppContext context, boolean up, boolean hours, int diff) {
		this(context, up, hours, diff, false);
	}

	public TimeAdjustButton(AppContext context, boolean up, boolean hours, int diff, boolean doubleSize) {
		super(context, context.icons().getArrow());
		setAlignmentX(CENTER_ALIGNMENT);
		this.up = up;
		this.hours = hours;
		this.diff = diff;
		this.doubleSize = doubleSize;
		setIcon(up);
	}

	@Override
	protected void setIcon(boolean on) {
		CharIcon charIcon = getContext().icons().getArrow();
		char c = ((on) ? charIcon.getOnChar() : charIcon.getOffChar());
		String text = (doubleSize) ? "" + c + " " + c : "" + c;
		setText(text);
	}

	@Override
	protected void clicked(ActionEvent event) {
		AlarmClock alarm = getContext().alarmClock();
		if (hours) {
			if (up) {
				alarm.setAlarmTime(alarm.getAlarmTime().plusHours(diff));
			} else {
				alarm.setAlarmTime(alarm.getAlarmTime().minusHours(diff));
			}
		} else {
			if (up) {
				alarm.setAlarmTime(alarm.getAlarmTime().plusMinutes(diff));
			} else {
				alarm.setAlarmTime(alarm.getAlarmTime().minusMinutes(diff));
			}
		}
	}
}
