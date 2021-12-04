package marcclaessens.alarmclock;

import marcclaessens.alarmclock.feature.RssFeedListener;

public interface RssFeed {
    void registerListener(RssFeedListener l);

    void source(String label, String source);
}
