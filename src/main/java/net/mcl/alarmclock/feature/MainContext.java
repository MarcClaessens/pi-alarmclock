package net.mcl.alarmclock.feature;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.Screen;

public class MainContext implements AppContext {
	private static AlarmThread ALARM_THREAD = new AlarmThread();

	private final AppPropertiesImpl appProperties = new AppPropertiesImpl();
	private final AlarmClock alarmclock = new AlarmClockImpl(ALARM_THREAD, appProperties.getAlarmRadio());
	private final WeatherReport weatherReport = new WeatherReportImpl(appProperties.getWeatherSource());
	private final Screen screen;
	private final RssFeed rss = new RssFeedImpl();

	public MainContext(Screen screen) {
		this.screen = screen;

		alarmclock.loadAlarmTime();
		ALARM_THREAD.start();
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
	public Screen screen() {
		return screen;
	}

	@Override
	public IconProvider icons() {
		return appProperties;
	}

	@Override
	public RssFeed rss() {
		return rss;
	}
}
