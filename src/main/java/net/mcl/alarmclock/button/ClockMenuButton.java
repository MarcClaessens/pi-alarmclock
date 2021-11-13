package net.mcl.alarmclock.button;

import java.awt.event.ActionEvent;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.swing.AbstractIconGlowButton;

/**
 * Button to go the clock scene.
 */
class ClockMenuButton extends AbstractIconGlowButton {
	private static final long serialVersionUID = 1L;

	public ClockMenuButton(AppContext context) {
		super(context, context.icons().getHome());
	}

	@Override
	protected void clicked(ActionEvent event) {
		getContext().screen().setClockScene();
	}

}
