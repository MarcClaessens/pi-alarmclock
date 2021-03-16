package net.mcl.alarmclock.feature;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.mcl.alarmclock.sound.Mp3Player;
import net.mcl.alarmclock.sound.Sound;

class AlarmThread implements Runnable {
	private static final Logger LOGGER = LogManager.getLogger(AlarmThread.class);

	private final Mp3Player player;
	private Sound sound = null;
	private boolean play;

	AlarmThread(String mixer) {
		super();
		player = new Mp3Player(mixer);
		Thread th = new Thread(this);
		th.setDaemon(true);
		th.start();
	}

	@Override
	public void run() {
		while (true) {

			if (sound != null && play) {
				play = false;
				player.play(sound);
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

	public void playSound(Sound sound) {
		LOGGER.debug("init playing !");
		this.sound = sound;
		this.play = true;
	}

	public void stopPlaying() {
		LOGGER.debug("stop playing !");
		player.stop();
		play = false;
	}
}