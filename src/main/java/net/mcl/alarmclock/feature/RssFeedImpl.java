package net.mcl.alarmclock.feature;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.mcl.alarmclock.RssFeed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RssFeedImpl implements RssFeed {
    private static final Logger LOGGER = LogManager.getLogger(RssFeed.class);

    private final List<RssFeedListener> feedlisteners = new ArrayList<>();
    private final int fetchCount;

    public RssFeedImpl(int fetchCount) {
        this.fetchCount = fetchCount;
    }

    @Override
    public void source(String label, String source) {
        if (feedlisteners.isEmpty()) {
            LOGGER.warn("No RSS listeners");
            return;
        }
        feedlisteners.stream().forEach(l -> l.rssLoading(label));

        try {
            SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(source)));
            final List<String> content = getRssContent(feed.getEntries());
            feedlisteners.stream().forEach(l -> l.rssContentChanged(content));
        } catch (IOException | FeedException e) {
            LOGGER.error(e);
        }

    }

    private List<String> getRssContent(List<SyndEntry> entries) {
        List<String> content = new ArrayList<>();
        int line = 0;
        for (SyndEntry entry : entries) {
            if (line < fetchCount) {
                content.add(new StringBuilder().append(line).append("- ").append(entry.getTitle()).toString());
            }
            line++;
        }
        return content;
    }

    @Override
    public void registerListener(RssFeedListener l) {
        feedlisteners.add(l);
    }
}
