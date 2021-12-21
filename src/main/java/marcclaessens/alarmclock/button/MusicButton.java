package marcclaessens.alarmclock.button;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import marcclaessens.alarmclock.AppContext;
import marcclaessens.alarmclock.feature.SoundSource;
import marcclaessens.alarmclock.swing.AbstractIconGlowButton;

/**
 * Button to toggle playing music.
 */
class MusicButton extends AbstractIconGlowButton {
	private static final long serialVersionUID = 1L;
	private static final List<MusicButton> INSTANCES = new ArrayList<>();
	private static boolean PLAYING = false;

	public MusicButton(AppContext context) {
		super(context, context.icons().getMusic(), false);
		INSTANCES.add(this);
	}

	@Override
	protected void clicked(ActionEvent event) {
		PLAYING = !PLAYING;

		if (PLAYING) {
			getContext().alarmClock().playSound(SoundSource.RADIO_CHANNEL);
		} else {
			getContext().alarmClock().stopSound(SoundSource.RADIO_CHANNEL);
		}
		for (MusicButton b : INSTANCES) {
			b.setIcon(PLAYING);
		}
	}

}
