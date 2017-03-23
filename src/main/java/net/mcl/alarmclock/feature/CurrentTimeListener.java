package net.mcl.alarmclock.feature;

import java.time.LocalTime;

public interface CurrentTimeListener {

    void updateCurrentTime(LocalTime t);
}
