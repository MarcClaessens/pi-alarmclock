package net.mcl.alarmclock.feature;

import java.time.LocalTime;

public interface AlarmClock {
    void setAlarmOn(boolean alarmOn);

    void toggleAlarm();

    boolean isAlarmOn();

    void registerTimeListener(CurrentTimeListener l);

    void registerAlarmListener(AlarmTimeListener l);

    LocalTime getAlarmTime();

    void minusAlarmTimeMinutes(int min);

    void plusAlarmTimeMinutes(int min);

    void plusAlarmTimeHours(int hours);

    void minusAlarmTimeHours(int hours);

    void setAlarmTime(LocalTime lt);

    void saveAlarmTime();

    void loadAlarmTime();

    void toggleMusic();

    boolean isMusicPlaying();
}
