package net.mcl.alarmclock.sound;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Swing uses a single thread so playing an MP3 will halt everything else unless
 * we launch another thread.
 * 
 * @author marc
 *
 */
public class SwingMp3Player extends Mp3Player implements Runnable {
	private static final Logger LOGGER = LogManager.getLogger(SwingMp3Player.class);

	private Sound sound = null;

	public SwingMp3Player(String mixerName) {
		super(mixerName);
		new Thread(this, "SwingMp3Player").start();
	}

	@Override
	public void run() {
		while (true) {
			if (sound != null) {
				LOGGER.info("Start playing {}", sound);
				super.play(sound);
				try {
					Thread.sleep(1_000L);
				} catch (InterruptedException e) {
					LOGGER.error("Interrupted thread", e);
					Thread.currentThread().interrupt();
				}
				sound = null;
				LOGGER.info("Finished playing {}", sound);
			} else {
				try {
					Thread.sleep(1_000L);
				} catch (InterruptedException e) {
					LOGGER.error("Interrupted thread", e);
					Thread.currentThread().interrupt();
				}
			}
		}
	}

	@Override
	public void play(final Sound sound) {
		this.sound = sound;
	}

	public void stop() {
		sound = null;
		super.stop();
	}
}
