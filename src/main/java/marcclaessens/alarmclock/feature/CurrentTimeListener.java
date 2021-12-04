package marcclaessens.alarmclock.feature;

import java.time.LocalTime;

public interface CurrentTimeListener {

    void updateCurrentTime(LocalTime t);
}
