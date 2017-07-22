package net.mcl.alarmclock;

import javax.swing.JComponent;

import net.mcl.alarmclock.feature.AlarmClock;
import net.mcl.alarmclock.feature.AppProperties;
import net.mcl.alarmclock.feature.IconProvider;
import net.mcl.alarmclock.feature.RssFeed;
import net.mcl.alarmclock.feature.WeatherReport;

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
