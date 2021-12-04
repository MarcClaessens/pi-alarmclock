package net.mcl.alarmclock.feature;

import net.mcl.alarmclock.sound.FileSound;
import net.mcl.alarmclock.sound.Sound;
import net.mcl.alarmclock.sound.WebMp3Sound;

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
