package marcclaessens.alarmclock.feature;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import marcclaessens.alarmclock.AlarmClock;
import marcclaessens.alarmclock.AppProperties;
import marcclaessens.alarmclock.sound.Mp3Player;
import marcclaessens.alarmclock.sound.Sound;
import marcclaessens.alarmclock.sound.SoundRepository;
import marcclaessens.alarmclock.sound.SoundSourceType;

class AlarmClockImpl implements AlarmClock, ActionListener {
	private static final Logger LOGGER = LogManager.getLogger(AlarmClockImpl.class);
	private static final String UPDATE_TIME = "UPDATE_TIME";

	private boolean alarmOn;
	private LocalTime alarmTime;

	private final List<CurrentTimeListener> timelisteners = new ArrayList<>();
	private final List<AlarmTimeListener> alarmlisteners = new ArrayList<>();

	private final AppProperties appProps;
	private final Mp3Player player;
	private final int louddelay;
	private final Timer timerFirstAlarm;

	public AlarmClockImpl(Mp3Player player, AppProperties appProps) {
		timerFirstAlarm = new Timer(500, this);
		timerFirstAlarm.setActionCommand(UPDATE_TIME);
		timerFirstAlarm.start();

		this.appProps = appProps;
		this.player = player;

		this.louddelay = appProps.getLoudAlarmActivationDelay();

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
			stopAnySound();
			stopSound(SoundRepository.get(SoundSourceType.REPEATING_ALARM));
			LOGGER.debug("alarm turned off");
		}
		this.alarmOn = alarmOn;
	}

	@Override
	public void playSound(Sound source) {
		if (!player.isPlaying(source)) {
			player.stop();
		}
		player.play(source);

	}

	@Override
	public void stopAnySound() {
		player.stop();
	}
	
	@Override
	public void stopSound(Sound source) {
		if (player.isPlaying(source)) {
			player.stop();
		}
	}

	@Override
	public void registerTimeListener(CurrentTimeListener l) {
		timelisteners.add(l);
	}

	@Override
	public void changeRadioChannel(int index) {
		Sound newSound = SoundRepository.get(SoundSourceType.RADIO_CHANNEL, index);
		appProps.setRadioAlarm(index);
		if (! player.isPlaying(newSound)) {
			playSound(newSound);
		}
	}

	@Override
	public void registerAlarmListener(AlarmTimeListener l) {
		alarmlisteners.add(l);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (UPDATE_TIME.equals(ae.getActionCommand())) {
			final LocalTime t = LocalTime.now();
			if (!timelisteners.isEmpty()) {
				timelisteners.parallelStream().forEach(l -> l.updateCurrentTime(t));
			}
			if (alarmOn) {
				if (isAlarmTime(t)) {
					Sound expectedSound = SoundRepository.get(SoundSourceType.RADIO_ALARM);
					if (!player.isPlaying(expectedSound)) {
						playSound(expectedSound);
					}
				} else if (isSecondAlarmTime(t)) { // repeat alarm until toggled off
					Sound expectedSound = SoundRepository.get(SoundSourceType.REPEATING_ALARM);
					if (!player.isPlaying(expectedSound)) {
						playSound(expectedSound);
					}
				}
			}
		}
	}

	private synchronized boolean isAlarmTime(LocalTime t) {
		if (alarmTime == null || !alarmOn) {
			return false;
		} else {
			long duration = Duration.between(alarmTime, t).toMillis();
			return duration > 0 && duration < 500;
		}
	}

	private synchronized boolean isSecondAlarmTime(LocalTime t) {
		if (alarmTime == null || !alarmOn) {
			return false;
		} else {
			LocalTime secondAlarm = alarmTime.plusMinutes(louddelay);
			long duration = Duration.between(secondAlarm, t).toMillis();
			boolean flag = duration > 0 && duration < 500;
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
