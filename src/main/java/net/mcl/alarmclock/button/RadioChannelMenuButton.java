package net.mcl.alarmclock.button;

import java.awt.event.ActionEvent;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.swing.AbstractIconGlowButton;

/**
 * Button to toggle playing music.
 */
class RadioChannelMenuButton extends AbstractIconGlowButton {
	public RadioChannelMenuButton(AppContext context) {
		super(context, context.icons().getRadioChannel());
	}

	@Override
	protected void clicked(ActionEvent event) {
		getContext().screen().setRadioChannelScene();
	}

}
