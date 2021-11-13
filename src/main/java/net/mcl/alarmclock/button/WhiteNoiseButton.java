package net.mcl.alarmclock.button;

import java.awt.event.ActionEvent;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.feature.SoundSources;
import net.mcl.alarmclock.swing.AbstractIconGlowButton;

/**
 * Button to toggle playing music.
 */
class WhiteNoiseButton extends AbstractIconGlowButton {
	private static final long serialVersionUID = 1L;
	private boolean playing = false;

	public WhiteNoiseButton(AppContext context) {
		super(context, context.icons().getWhiteNoise(), false);
	}

	@Override
	protected void clicked(ActionEvent event) {
		playing = !playing;

		if (playing) {
			getContext().alarmClock().playSound(SoundSources.WHITENOICE);
		} else {
			getContext().alarmClock().stopSound();
		}
		setIcon(playing);
	}

}
