package net.mcl.alarmclock.feature;

import net.mcl.alarmclock.sound.Mp3Player;
import net.mcl.alarmclock.sound.Sound;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class AlarmThread extends Thread {
    private static final Logger LOGGER = LogManager.getLogger(AlarmThread.class);

    private Mp3Player player = new Mp3Player();
    private Sound sound = null;
    private boolean play;

    AlarmThread() {
        super(new ThreadGroup("leave me outside of FX"), "AlarmThread");
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {

            if (sound != null && play) {
                play = false;
                LOGGER.debug("playing !");
                player.play(sound);
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException e) {
                    LOGGER.error("Interrupted thread", e);
                    Thread.currentThread().interrupt();
                }
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