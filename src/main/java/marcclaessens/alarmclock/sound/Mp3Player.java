package marcclaessens.alarmclock.sound;

import java.io.InputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.Player;

public class Mp3Player {
	private static final Logger LOGGER = LogManager.getLogger(Mp3Player.class);
	private static boolean mixerListLogged;

	private final String mixerName;
	private Player player = null;
	private boolean playing = false;
	private Sound sound;

	public Mp3Player(String mixerName) {
		this.mixerName = mixerName;
	}

	/**
	 * Plays the given sound.
	 * 
	 * @param sound - a sound file streamed from internet or local file
	 */
	public void play(final Sound sound) {
		this.sound = sound;
		play();
	}

	public synchronized boolean isPlaying(Sound sound) {
		return sound.equals(this.sound);
	}

	public synchronized boolean isPlaying() {
		return playing;
	}

	public void stop() {
		if (player != null) {
			player.close();
			player = null;
			sound = null;
			playing = false;
		}
	}

	protected void play() {
		if (sound != null) {
			try (InputStream is = sound.getSoundStream()) {
				player = new Player(is, getAudioDevice());
				playing = true;
				player.play();
			} catch (Exception e) {
				LOGGER.error("Play error", e);
			} finally {
				playing = false;
			}
		}
	}

	protected Sound getSound() {
		return sound;
	}

	protected void setSound(Sound sound) {
		this.sound = sound;
	}

	private AudioDevice getAudioDevice() {
		Mixer foundMixer = null;
		Mixer.Info[] mixers = AudioSystem.getMixerInfo();
		for (Mixer.Info mixer : mixers) {
			if (mixer.getName().equals(mixerName)) {
				if (!mixerListLogged) {
					LOGGER.debug("<active> Mixer found with name '" + mixer.getName() + "' and description "
							+ mixer.getDescription());
					mixerListLogged = true;
				}
				foundMixer = AudioSystem.getMixer(mixer);
			} else {
				if (!mixerListLogged) {
					LOGGER.debug("         Mixer found with name '" + mixer.getName() + "' and description "
							+ mixer.getDescription());
				}
			}
		}

		if (foundMixer == null) {
			LOGGER.debug("Using default audio device");
		}
		return new MixerJavaSoundAudioDevice(foundMixer);
	}

}
