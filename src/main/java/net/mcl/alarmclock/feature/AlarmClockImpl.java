package net.mcl.alarmclock.feature;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.mcl.alarmclock.AlarmClock;
import net.mcl.alarmclock.AppProperties;
import net.mcl.alarmclock.sound.FileSound;
import net.mcl.alarmclock.sound.Sound;
import net.mcl.alarmclock.sound.WebMp3Sound;

class AlarmClockImpl implements AlarmClock, ActionListener {
	private static final Logger LOGGER = LogManager.getLogger(AlarmClockImpl.class);
	private static final String PRIMARY = "PRIMARY";
	private static final String SECUNDARY = "SECUNDARY";

	private boolean alarmOn;
	private boolean alarmRunning;
	private LocalTime alarmTime;
	private boolean radioPlaying;

	private final List<CurrentTimeListener> timelisteners = new ArrayList<>();
	private final List<AlarmTimeListener> alarmlisteners = new ArrayList<>();
	private String radioInput;
	private final String secondAlarm;

	private final AlarmThread alarmthread;
	private final AppProperties appProps;
	private final int louddelay;
	private final Timer timerPrimary;
	private final Timer timerSecundary;

	public AlarmClockImpl(AlarmThread alarmthread, AppProperties appProps) {

		timerPrimary = new Timer(500, this);
		timerPrimary.setActionCommand(PRIMARY);
		timerPrimary.start();

		this.alarmthread = alarmthread;
		this.appProps = appProps;
		this.radioInput = appProps.getRadioAlarm();
		this.secondAlarm = appProps.getLoudAlarm();
		this.louddelay = appProps.getLoudAlarmActivationDelay();

		int repeatdelay = appProps.getLoudAlarmRepeatDelay() * 1000;
		timerSecundary = new Timer(repeatdelay, this);
		timerSecundary.setActionCommand(SECUNDARY);

		setAlarmTime(appProps.getAlarmTime());
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
	 * Check if alarm is on.
	 */
	@Override
	public boolean isAlarmOn() {
		return alarmOn;
	}

	private void activatePrimaryAlarm() {
		if (!alarmRunning) {
			alarmRunning = true;
			play(radioInput);
		}
	}

	private void activateSecundaryAlarm() {
		if (!timerSecundary.isRunning()) {
			timerSecundary.start();
		}
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
		alarmthread.stopPlaying();
	}

	@Override
	public void registerTimeListener(CurrentTimeListener l) {
		timelisteners.add(l);
	}

	@Override
	public void changeRadioChannel(String radioChannel) {
		this.radioInput = radioChannel;
		appProps.setRadioAlarm(radioChannel);
		if (isMusicPlaying()) {
			toggleMusic();
			toggleMusic();
		}
	}

	@Override
	public void registerAlarmListener(AlarmTimeListener l) {
		alarmlisteners.add(l);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (PRIMARY.equals(ae.getActionCommand())) {
			final LocalTime t = LocalTime.now();
			if (!timelisteners.isEmpty()) {
				timelisteners.parallelStream().forEach(l -> l.updateCurrentTime(t));
			}
			if (isAlarmTime(t)) {
				activatePrimaryAlarm();
			}
			if (isSecondAlarmTime(t)) {
				activateSecundaryAlarm();
			}
		}
		if (SECUNDARY.equals(ae.getActionCommand())) {
			if (alarmRunning) {
				play(secondAlarm);
			} else {
				timerSecundary.stop();
			}
		}
	}

	private synchronized boolean isAlarmTime(LocalTime t) {
		if (alarmTime == null || !alarmOn) {
			return false;
		} else {
			long duration = Duration.between(alarmTime, t).toMillis();
			return duration > 0 && duration < 1500;
		}
	}

	private synchronized boolean isSecondAlarmTime(LocalTime t) {
		if (alarmTime == null || !alarmOn) {
			return false;
		} else {
			LocalTime secondAlarm = alarmTime.plusMinutes(louddelay);
			long duration = Duration.between(secondAlarm, t).toMillis();
			boolean flag = duration > 0 && duration < 1500;
			return flag;
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
	public LocalTime getAlarmTime() {
		return alarmTime;
	}

	@Override
	public void saveAlarmTime() {
		appProps.setAlarmTime(alarmTime);
	}

}
