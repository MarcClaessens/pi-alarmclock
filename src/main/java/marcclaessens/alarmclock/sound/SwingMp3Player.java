package marcclaessens.alarmclock.sound;

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

	private final Thread runner;

	public SwingMp3Player(String mixerName) {
		super(mixerName);
		runner = new Thread(this, "SwingMp3Player");
		runner.start();
	}

	@Override
	public void run() {
		while (true) {
			Sound sound = getSound();
			if (sound != null) {
				LOGGER.info("Start playing {}", sound);
				try {
					super.play();
					if (getSound() == null || sound.getDelayMillis() == -1) {
						LOGGER.info("Finished playing {}", sound);
					} else {
						sleep(sound.getDelayMillis());
					}
				} catch (Exception e) {
					LOGGER.error("Unexpected end of play", e);
				}
			} else {
				sleep(250L);
			}
		}
	}

	private void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			LOGGER.trace("Interrupted thread", e);
		}
	}

	@Override
	public void play(final Sound sound) {
		super.setSound(sound);
	}

	@Override
	public void stop() {
		runner.interrupt();
		super.stop();
		while (super.isPlaying()) {
			sleep(250L);
		}
	}
}
