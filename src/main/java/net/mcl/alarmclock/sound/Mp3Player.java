package net.mcl.alarmclock.sound;

import java.io.InputStream;

import javazoom.jl.player.Player;

public class Mp3Player {
    private Player player = null;

    public Mp3Player() {
    }

    public void play(final Sound sound) {
        if (sound != null) {
            try (InputStream is = sound.getSoundStream()) {
                player = new Player(is);
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        if (player != null) {
            player.close();
            player = null;
        }        
    }

}
