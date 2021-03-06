package marcclaessens.alarmclock;

import java.time.LocalTime;

import marcclaessens.alarmclock.feature.AlarmTimeListener;
import marcclaessens.alarmclock.feature.CurrentTimeListener;
import marcclaessens.alarmclock.feature.SoundSource;

public interface AlarmClock {
	void setAlarmOn(boolean alarmOn);

	void toggleAlarm();

	boolean isAlarmOn();

	void registerTimeListener(CurrentTimeListener l);

	void registerAlarmListener(AlarmTimeListener l);

	LocalTime getAlarmTime();

	void setAlarmTime(LocalTime lt);

	void saveAlarmTime();

	void playSound(SoundSource source);

	void stopSound(SoundSource source);

	void changeRadioChannel(String radioChannel);
}
