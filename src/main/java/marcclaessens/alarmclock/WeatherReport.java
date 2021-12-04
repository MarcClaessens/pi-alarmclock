package marcclaessens.alarmclock;

import marcclaessens.alarmclock.feature.WeatherReportListener;

public interface WeatherReport {
    void setWeatherReportOn(boolean messageOn);

    void toggleWeatherReportOn();

    void registerListener(WeatherReportListener l);

    boolean isWeatherReportOn();

}
