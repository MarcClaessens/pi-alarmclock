package marcclaessens.alarmclock.feature;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import marcclaessens.alarmclock.RssFeed;

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

		try (CloseableHttpClient client = HttpClients.createMinimal()) {
			HttpUriRequest request = new HttpGet(source);
			try (CloseableHttpResponse response = client.execute(request);
					InputStream stream = response.getEntity().getContent();
					XmlReader xmlReader = new XmlReader(stream)) {

				SyndFeedInput input = new SyndFeedInput();
				SyndFeed feed = input.build(xmlReader);

				final List<String> content = getRssContent(feed.getEntries());
				feedlisteners.stream().forEach(l -> l.rssContentChanged(content));
			} catch (Exception e) {
				LOGGER.error("Error fetching/parsing RSS feed from " + source, e);
			}
		} catch (IOException e1) {
			LOGGER.error("Error fetching/parsing RSS feed from " + source, e1);
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
