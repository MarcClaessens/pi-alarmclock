package net.mcl.alarmclock.feature;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.mcl.alarmclock.AlarmClock;
import net.mcl.alarmclock.AppProperties;
import net.mcl.alarmclock.sound.Mp3Player;

class AlarmClockImpl implements AlarmClock, ActionListener {
	private static final Logger LOGGER = LogManager.getLogger(AlarmClockImpl.class);
	private static final String PRIMARY = "PRIMARY";
	private static final String SECUNDARY = "SECUNDARY";

	private boolean alarmOn;
	private LocalTime alarmTime;

	private final List<CurrentTimeListener> timelisteners = new ArrayList<>();
	private final List<AlarmTimeListener> alarmlisteners = new ArrayList<>();

	private final AppProperties appProps;
	private final Mp3Player player;
	private final int louddelay;
	private final Timer timerPrimary;
	private final Timer timerSecundary;

	public AlarmClockImpl(Mp3Player player, AppProperties appProps) {
		timerPrimary = new Timer(500, this);
		timerPrimary.setActionCommand(PRIMARY);
		timerPrimary.start();

		this.appProps = appProps;
		this.player = player;

		this.louddelay = appProps.getLoudAlarmActivationDelay();
		int repeatdelay = appProps.getLoudAlarmRepeatDelay() * 1000;

		timerSecundary = new Timer(repeatdelay, this);
		timerSecundary.setActionCommand(SECUNDARY);

		setAlarmTime(appProps.getAlarmTime());
	}

	/**
	 * Check if alarm is on.
	 */
	@Override
	public boolean isAlarmOn() {
		return alarmOn;
	}

	/**
	 * Set alarmOn flag to opposite value.
	 */
	@Override
	public void toggleAlarm() {
		setAlarmOn(!this.alarmOn);
	}

	/**
	 * Set alarmOn flag.
	 */
	@Override
	public void setAlarmOn(boolean alarmOn) {
		if (!alarmOn) {
			stopSound();
			timerSecundary.stop();
			LOGGER.debug("alarm turned off");
		}
		this.alarmOn = alarmOn;
	}

	@Override
	public void playSound(SoundSources source) {
		if (player.isPlaying()) {
			stopSound();
		}
		player.play(source.getSound());

	}

	@Override
	public void stopSound() {
		player.stop();
	}

	private void activateSecundaryAlarm() {
		if (!timerSecundary.isRunning()) {
			timerSecundary.start();
		}
	}

	@Override
	public void registerTimeListener(CurrentTimeListener l) {
		timelisteners.add(l);
	}

	@Override
	public void changeRadioChannel(String radioChannel) {
		SoundSources.RADIO.setSource(radioChannel);
		appProps.setRadioAlarm(radioChannel);
		if (player.isPlaying()) {
			playSound(SoundSources.RADIO);
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
				playSound(SoundSources.RADIO);
			}
			if (isSecondAlarmTime(t)) {
				activateSecundaryAlarm();
			}
		}
		if (SECUNDARY.equals(ae.getActionCommand())) {
			if (alarmOn) { // repeat alarm until toggled off
				playSound(SoundSources.ALARM);
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
