package marcclaessens.alarmclock.feature;

import java.time.LocalTime;

public interface AlarmTimeListener {

    void updateAlarmTime(LocalTime t);
}
