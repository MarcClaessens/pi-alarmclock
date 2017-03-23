package net.mcl.alarmclock;

import net.mcl.alarmclock.feature.AlarmClock;
import net.mcl.alarmclock.feature.AppProperties;
import net.mcl.alarmclock.feature.IconProvider;
import net.mcl.alarmclock.feature.RssFeed;
import net.mcl.alarmclock.feature.WeatherReport;

/**
 * Application context.  
 * Enables application wide configuration on all features, without having to resort to singletons.
 */
public interface AppContext {
    public AlarmClock alarmClock();

    public AppProperties props();

    public IconProvider icons();

    public WeatherReport weather();

    public Screen screen();
    
    public RssFeed rss();
}
