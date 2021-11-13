package net.mcl.alarmclock.button;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.feature.SoundSources;
import net.mcl.alarmclock.swing.AbstractIconGlowButton;

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
			getContext().alarmClock().playSound(SoundSources.RADIO);
		} else {
			getContext().alarmClock().stopSound();
		}
		for (MusicButton b : INSTANCES) {
			b.setIcon(PLAYING);
		}
	}

}
