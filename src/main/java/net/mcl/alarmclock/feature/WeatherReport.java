package net.mcl.alarmclock.feature;

public interface WeatherReport {
    void setWeatherReportOn(boolean messageOn);

    void toggleWeatherReportOn();

    void registerListener(WeatherReportListener l);

    boolean isWeatherReportOn();

}
