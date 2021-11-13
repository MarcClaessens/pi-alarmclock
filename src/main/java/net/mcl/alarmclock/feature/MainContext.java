package net.mcl.alarmclock.feature;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import net.mcl.alarmclock.AlarmClock;
import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.AppProperties;
import net.mcl.alarmclock.AppScreen;
import net.mcl.alarmclock.IconProvider;
import net.mcl.alarmclock.RssFeed;
import net.mcl.alarmclock.WeatherReport;
import net.mcl.alarmclock.sound.Mp3Player;
import net.mcl.alarmclock.sound.SwingMp3Player;

public class MainContext implements AppContext {

	private final AppProperties appProperties = new AppPropertiesImpl();
	private final IconProvider iconProvider = (AppPropertiesImpl) appProperties;
	private final WeatherReport weatherReport = new WeatherReportImpl(appProperties.getWeatherSource());
	private final RssFeed rss = new RssFeedImpl(appProperties.getRssFetchCount());

	private final Mp3Player player = new SwingMp3Player(appProperties.getMixer());
	private final AlarmClock alarmclock = new AlarmClockImpl(player, appProperties);

	private final AppScreen screen;

	private int rightClicks;

	public MainContext(AppScreen screen) {
		this.screen = screen;
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

	@Override
	public void registerRightClickListener(JComponent component) {
		component.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e) || SwingUtilities.isMiddleMouseButton(e)) {
					rightClicks++;
					if (rightClicks == 4) {
						System.exit(0);
					}
				}
			}
		});
	}
}
