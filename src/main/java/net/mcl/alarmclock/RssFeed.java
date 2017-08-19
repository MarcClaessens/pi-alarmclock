package net.mcl.alarmclock;

import net.mcl.alarmclock.feature.RssFeedListener;

public interface RssFeed {
    void registerListener(RssFeedListener l);

    void source(String label, String source);
}
