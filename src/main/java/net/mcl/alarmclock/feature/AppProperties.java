package net.mcl.alarmclock.feature;

import java.util.List;

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
}
