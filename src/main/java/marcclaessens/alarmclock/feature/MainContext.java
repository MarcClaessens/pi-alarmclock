package marcclaessens.alarmclock.feature;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import marcclaessens.alarmclock.AlarmClock;
import marcclaessens.alarmclock.AppContext;
import marcclaessens.alarmclock.AppProperties;
import marcclaessens.alarmclock.AppScreen;
import marcclaessens.alarmclock.IconProvider;
import marcclaessens.alarmclock.RssFeed;
import marcclaessens.alarmclock.WeatherReport;
import marcclaessens.alarmclock.sound.Mp3Player;
import marcclaessens.alarmclock.sound.SwingMp3Player;

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
