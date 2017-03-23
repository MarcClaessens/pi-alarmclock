package net.mcl.alarmclock.feature;

import java.util.List;

public interface AppProperties {
    String getAlarmLoudSound();

    String getAlarmRadio();

    int getButtonCount();

    int getButtonLeftCount();

    String getButtonType(int buttonNr);

	String getWeatherSource();
    
    List<RssSource> getRssSources();
}
