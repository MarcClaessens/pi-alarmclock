package marcclaessens.alarmclock.button;

import java.awt.event.ActionEvent;

import marcclaessens.alarmclock.AppContext;
import marcclaessens.alarmclock.feature.SoundSource;
import marcclaessens.alarmclock.swing.AbstractIconGlowButton;

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
			getContext().alarmClock().playSound(SoundSource.WHITENOISE);
		} else {
			getContext().alarmClock().stopSound(SoundSource.WHITENOISE);
		}
		setIcon(playing);
	}

}
