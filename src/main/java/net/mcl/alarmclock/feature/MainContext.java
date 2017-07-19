package net.mcl.alarmclock.feature;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.AppScreen;

public class MainContext implements AppContext {
    private static AlarmThread ALARM_THREAD = new AlarmThread();

    private final AppProperties appProperties = new AppPropertiesImpl();
    private final IconProvider iconProvider = (AppPropertiesImpl) appProperties;
    private final AlarmClock alarmclock = new AlarmClockImpl(ALARM_THREAD, appProperties);
    private final WeatherReport weatherReport = new WeatherReportImpl(appProperties.getWeatherSource());
    private final AppScreen screen;
    private final RssFeed rss = new RssFeedImpl(appProperties.getRssFetchCount());

    public MainContext(AppScreen screen) {
        this.screen = screen;
        alarmclock.loadAlarmTime();
    }

    @Override
    public AlarmClock alarmClock() {
        return alarmclock;
    }

    @Override
    public AppProperties props() {
        return appProperties;
    }

    @Override
    public WeatherReport weather() {
        return weatherReport;
    }

    @Override
    public AppScreen screen() {
        return screen;
    }

    @Override
    public IconProvider icons() {
        return iconProvider;
    }

    @Override
    public RssFeed rss() {
        return rss;
    }
}
