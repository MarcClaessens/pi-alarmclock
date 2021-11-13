package net.mcl.alarmclock.button;

import java.awt.event.ActionEvent;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.swing.AbstractIconGlowButton;

/**
 * Button to go the menu for setting the alarm time.
 */
class AlarmtimeMenuButton extends AbstractIconGlowButton {
	private static final long serialVersionUID = 1L;

	public AlarmtimeMenuButton(AppContext context) {
		super(context, context.icons().getClock());
	}

	@Override
	protected void clicked(ActionEvent event) {
		getContext().screen().setAlarmTimeScene();
	}

}
