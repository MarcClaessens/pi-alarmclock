package net.mcl.alarmclock.feature;

public interface RssFeed {
	void registerListener(RssFeedListener l);
	void source(String label, String source);
}
