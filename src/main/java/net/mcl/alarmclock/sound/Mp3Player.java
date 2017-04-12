package net.mcl.alarmclock.sound;

import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javazoom.jl.player.Player;

public class Mp3Player {
    private static final Logger LOGGER = LogManager.getLogger(Mp3Player.class);

    private Player player = null;
    private boolean playing = false;

    public Mp3Player() {
    }

    /**
     * Plays the given sound.
     * 
     * @param sound
     *            - a sound file streamed from internet or local file
     */
    public void play(final Sound sound) {
        if (sound != null) {
            try (InputStream is = sound.getSoundStream()) {
                player = new Player(is);
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
