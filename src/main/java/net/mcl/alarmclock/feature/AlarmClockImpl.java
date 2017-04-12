package net.mcl.alarmclock.feature;

import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import net.mcl.alarmclock.sound.FileSound;
import net.mcl.alarmclock.sound.Sound;
import net.mcl.alarmclock.sound.WebMp3Sound;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class AlarmClockImpl implements AlarmClock {
    private static final Logger LOGGER = LogManager.getLogger(AlarmClockImpl.class);

    private boolean alarmOn;
    private boolean alarmRunning;
    private LocalTime alarmTime;
    private boolean radioPlaying;

    private final List<CurrentTimeListener> timelisteners = new ArrayList<>();
    private final List<AlarmTimeListener> alarmlisteners = new ArrayList<>();
    private final String radioInput;
    private final String secondAlarm;
    private final AlarmThread alarmthread;
    private final int repeatdelay;
    private final int louddelay;

    public AlarmClockImpl(AlarmThread alarmthread, AppProperties appprops) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500L), this::updateTime));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        this.alarmthread = alarmthread;
        this.radioInput = appprops.getRadioAlarm();
        this.secondAlarm = appprops.getLoudAlarm();
        this.repeatdelay = appprops.getLoudAlarmRepeatDelay() * 1000;
        this.louddelay = appprops.getLoudAlarmActivationDelay() * 60 * 1000; 
    }

    /**
     * Set alarmOn flag.
     */
    @Override
    public void setAlarmOn(boolean alarmOn) {
        if (!alarmOn) {
            alarmRunning = false;
            stop();
            LOGGER.debug("alarm turned off");
        }
        this.alarmOn = alarmOn;
    }

    /**
     * Toggle radio.
     */
    @Override
    public void toggleMusic() {
        if (!radioPlaying) {
            play(radioInput);
        } else {
            stop();
        }
        radioPlaying = !radioPlaying;
    }

    /**
     * Set alarmOn flag to opposite value. 
     */
    @Override
    public void toggleAlarm() {
        setAlarmOn(!this.alarmOn);
    }
    
    /**
     * Check if music is playing.
     */
    @Override
    public boolean isMusicPlaying() {
        return radioPlaying;
    }

    /**
     * Check if alarm is on
     */
    @Override
    public boolean isAlarmOn() {
        return alarmOn;
    }

    private void activatePrimaryAlarm() {
        if (!alarmRunning) {
            alarmRunning = true;
            play(radioInput);
            prepareSecundaryAlarm();
        }
    }
    
    private void activateSecundaryAlarm(ActionEvent event) {
        while (alarmRunning) {
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(repeatdelay), this::repeatSecundaryAlarm));
            timeline.setCycleCount(1);
            timeline.play();
        }
    }
    
    private void repeatSecundaryAlarm(ActionEvent event) {
    	play(secondAlarm);
    }

    private void prepareSecundaryAlarm() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(louddelay), this::activateSecundaryAlarm));
        timeline.setCycleCount(1);
        timeline.play();
	}

	private void play(String input) {
		alarmthread.stopPlaying();
        LOGGER.debug("Alarm is running for " + input);
        Sound sound;
        if (input.startsWith("http")) {
            sound = new WebMp3Sound(input);
        } else {
            sound = new FileSound(new File(input));
        }
        alarmthread.playSound(sound);
    }

    private void stop() {
        if (alarmthread.isAlive()) {
            alarmthread.stopPlaying();
        }
    }

    @Override
    public void registerTimeListener(CurrentTimeListener l) {
        timelisteners.add(l);
    }

    @Override
    public void registerAlarmListener(AlarmTimeListener l) {
        alarmlisteners.add(l);
    }

    private void updateTime(ActionEvent event) {
        final LocalTime t = LocalTime.now();
        if (!timelisteners.isEmpty()) {
            timelisteners.parallelStream().forEach(l -> l.updateCurrentTime(t));
        }
        if (isAlarmTime(t)) {
            activatePrimaryAlarm();
        }
    }

    private synchronized boolean isAlarmTime(LocalTime t) {
        if (alarmTime == null || !alarmOn) {
            return false;
        } else {
            return alarmTime.getHour() == t.getHour() && alarmTime.getMinute() == t.getMinute();
        }
    }

    private void fireAlarmChangedEvent() {
        alarmlisteners.parallelStream().forEach(l -> l.updateAlarmTime(alarmTime));
    }

    @Override
    public void setAlarmTime(LocalTime lt) {
        this.alarmTime = lt;
        fireAlarmChangedEvent();
    }

    @Override
    public void plusAlarmTimeHours(int hours) {
        setAlarmTime(alarmTime.plusHours(hours));
    }

    @Override
    public void minusAlarmTimeHours(int hours) {
        setAlarmTime(alarmTime.minusHours(hours));
    }

    @Override
    public void plusAlarmTimeMinutes(int min) {
        setAlarmTime(alarmTime.plusMinutes(min));
    }

    @Override
    public void minusAlarmTimeMinutes(int min) {
        setAlarmTime(alarmTime.minusMinutes(min));
    }

    @Override
    public LocalTime getAlarmTime() {
        return alarmTime;
    }

    @Override
    public void saveAlarmTime() {
        Preferences prefs = Preferences.userRoot().node(getClass().getName());
        prefs.putInt("alarm.hour", alarmTime.getHour());
        prefs.putInt("alarm.minutes", alarmTime.getMinute());
    }

    @Override
    public void loadAlarmTime() {
        Preferences prefs = Preferences.userRoot().node(getClass().getName());
        int h = prefs.getInt("alarm.hour", 8);
        int m = prefs.getInt("alarm.minutes", 0);
        setAlarmTime(LocalTime.of(h, m));
    }
}
