package marcclaessens.alarmclock.feature;

import marcclaessens.alarmclock.sound.FileSound;
import marcclaessens.alarmclock.sound.Sound;
import marcclaessens.alarmclock.sound.WebMp3Sound;

public enum SoundSource {
	RADIO, ALARM, WHITENOICE;

	private Sound sound;

	public void setSource(String source, int delayMillis) {
		if (source.startsWith("http")) {
			sound = new WebMp3Sound(source, delayMillis);
		} else {
			sound = new FileSound(source, delayMillis);
		}
	}

	public Sound getSound() {
		return sound;
	}
}
