package marcclaessens.alarmclock.button;

import java.awt.event.ActionEvent;

import marcclaessens.alarmclock.AppContext;
import marcclaessens.alarmclock.swing.AbstractIconGlowButton;

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
