package net.mcl.alarmclock.button;

import java.awt.event.ActionEvent;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.swing.AbstractIconGlowButton;

/**
 * Button to go to the RSS menu.
 */
class RssMenuButton extends AbstractIconGlowButton {
	private static final long serialVersionUID = 1L;

	public RssMenuButton(AppContext context) {
		super(context, context.icons().getRss());
	}

	@Override
	protected void clicked(ActionEvent event) {
		getContext().screen().setRssScene();
	}
}
