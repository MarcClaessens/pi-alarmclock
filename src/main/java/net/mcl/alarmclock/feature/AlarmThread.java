package net.mcl.alarmclock.feature;

import net.mcl.alarmclock.sound.Mp3Player;
import net.mcl.alarmclock.sound.Sound;

class AlarmThread extends Thread {
    private Mp3Player player = new Mp3Player();;
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
                System.out.println("playing !");
                player.play(sound);
                try {
                    Thread.sleep(10l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Thread.sleep(1_000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void playSound(Sound sound) {
        System.out.println("init playing !");
        this.sound = sound;
        this.play = true;
    }

    public void stopPlaying() {
        System.out.println("stop playing !");
        player.stop();
        play = false;
    }
}