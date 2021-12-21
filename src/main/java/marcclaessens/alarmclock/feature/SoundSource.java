package marcclaessens.alarmclock.feature;

import java.io.InputStream;

import marcclaessens.alarmclock.sound.FileSound;
import marcclaessens.alarmclock.sound.Sound;
import marcclaessens.alarmclock.sound.WebMp3Sound;

public enum SoundSource implements Sound {
	RADIO_CHANNEL, RADIO_ALARM, REPEATING_ALARM, WHITENOISE;

	private Sound sound = null;;

	public void setSource(String source, int delayMillis) {
		if (source.startsWith("http")) {
			sound = new WebMp3Sound(source, delayMillis);
		} else {
			sound = new FileSound(source, delayMillis);
		}
	}

	@Override
	public InputStream getSoundStream() {
		if (sound != null) {
			return sound.getSoundStream();
		} else {
			return null;
		}
	}

	@Override
	public void alterSource(String source) {
		if (sound != null) {
			sound.alterSource(source);
		}
	}

	@Override
	public int getDelayMillis() {
		if (sound != null) {
			return sound.getDelayMillis();
		} else {
			return 0;
		}
	}
}
