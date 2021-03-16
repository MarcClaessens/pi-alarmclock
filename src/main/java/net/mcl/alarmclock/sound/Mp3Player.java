package net.mcl.alarmclock.sound;

import java.io.InputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.Player;

public class Mp3Player {
	private static final Logger LOGGER = LogManager.getLogger(Mp3Player.class);

	private final AudioDevice audioDevice;
	private Player player = null;
	private boolean playing = false;

	public Mp3Player(String mixerName) {
		Mixer foundMixer = null;
		Mixer.Info[] mixers = AudioSystem.getMixerInfo();
		for (Mixer.Info mixer : mixers) {
			if (mixer.getName().equals(mixerName)) {
				LOGGER.info("<active> Mixer found with name '" + mixer.getName() + "' and description "
						+ mixer.getDescription());
				foundMixer = AudioSystem.getMixer(mixer);
			} else {
				LOGGER.info("         Mixer found with name '" + mixer.getName() + "' and description "
						+ mixer.getDescription());
			}
		}

		if (foundMixer == null) {
			LOGGER.info("Using default audio device");
		}
		audioDevice = new MixerJavaSoundAudioDevice(foundMixer);
	}

	/**
	 * Plays the given sound.
	 * 
	 * @param sound - a sound file streamed from internet or local file
	 */
	public void play(final Sound sound) {
		if (sound != null) {
			try (InputStream is = sound.getSoundStream()) {
				player = new Player(is, audioDevice);
				playing = true;
				player.play();
			} catch (Exception e) {
				LOGGER.error(e);
			} finally {
				playing = false;
			}
		}
	}

	/**
	 * Stop playing.
	 */
	public void stop() {
		if (player != null) {
			player.close();
			player = null;
		}
	}

	public boolean isPlaying() {
		return playing;
	}
}
