package net.mcl.alarmclock;

import java.awt.Color;

public interface AppScreen {

    void setAlarmTimeScene();

    void setClockScene();

    void setRssScene();

    void setColorScene();

    void setMenuPanel(boolean active);

    void setForeGroundColor(Color color);
}
