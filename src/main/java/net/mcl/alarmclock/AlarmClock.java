package net.mcl.alarmclock;

import java.time.LocalTime;

import net.mcl.alarmclock.feature.AlarmTimeListener;
import net.mcl.alarmclock.feature.CurrentTimeListener;

public interface AlarmClock {
	void setAlarmOn(boolean alarmOn);

	void toggleAlarm();

	boolean isAlarmOn();

	void registerTimeListener(CurrentTimeListener l);

	void registerAlarmListener(AlarmTimeListener l);

	LocalTime getAlarmTime();

	void setAlarmTime(LocalTime lt);

	void saveAlarmTime();

	void toggleMusic();

	boolean isMusicPlaying();

	void changeRadioChannel(String radioChannel);
}
