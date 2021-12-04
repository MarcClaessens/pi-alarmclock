package marcclaessens.alarmclock.button;

import java.awt.event.ActionEvent;

import marcclaessens.alarmclock.AppContext;
import marcclaessens.alarmclock.swing.AbstractIconGlowButton;

/**
 * Button to toggle playing music.
 */
class RadioChannelMenuButton extends AbstractIconGlowButton {
	private static final long serialVersionUID = 1L;

	public RadioChannelMenuButton(AppContext context) {
		super(context, context.icons().getRadioChannel());
	}

	@Override
	protected void clicked(ActionEvent event) {
		getContext().screen().setRadioChannelScene();
	}

}
