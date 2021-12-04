package marcclaessens.alarmclock;

import javax.swing.JComponent;

/**
 * Application context. Enables application wide sharing on all features,
 * without having to resort to singletons.
 */
public interface AppContext {
    public AlarmClock alarmClock();

    public AppProperties props();

    public IconProvider icons();

    public WeatherReport weather();

    public AppScreen screen();

    public RssFeed rss();

    public void registerRightClickListener(JComponent component);
}
