package net.mcl.alarmclock;

import java.awt.Color;
import java.time.LocalTime;
import java.util.List;

import net.mcl.alarmclock.feature.RssSource;

public interface AppProperties {
    String getLoudAlarm();

    String getRadioAlarm();

    int getLoudAlarmRepeatDelay();

    int getLoudAlarmActivationDelay();

    int getButtonCount();

    int getButtonLeftCount();

    String getButtonType(int buttonNr);

    String getWeatherSource();

    List<RssSource> getRssSources();

    int getRssFetchCount();

    String getCustomFontSizes();

    /**
     * Sets the alarm time in HH:MM
     */
    void setAlarmTime(LocalTime time);

    /**
     * Gets the alarm time in HH:MM (array element 0 is hours, array element 1
     * is
     */
    LocalTime getAlarmTime();

    void setForeGroundColor(Color color);

    Color getForeGroundColor();

    void setBackGroundColor(Color color);

    Color getBackGroundColor();

}