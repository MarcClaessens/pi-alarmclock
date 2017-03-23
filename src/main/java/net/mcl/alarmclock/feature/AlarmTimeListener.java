package net.mcl.alarmclock.feature;

import java.time.LocalTime;

public interface AlarmTimeListener {

    void updateAlarmTime(LocalTime t);
}
