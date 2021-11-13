package net.mcl.alarmclock.feature;

import java.io.File;

import net.mcl.alarmclock.sound.FileSound;
import net.mcl.alarmclock.sound.Sound;
import net.mcl.alarmclock.sound.WebMp3Sound;

public enum SoundSources {
	RADIO, ALARM, WHITENOICE;

	private Sound sound;

	public void setSource(String source) {
		if (source.startsWith("http")) {
			sound = new WebMp3Sound(source);
		} else {
			sound = new FileSound(new File(source));
		}
	}

	public Sound getSound() {
		return sound;
	}
}
