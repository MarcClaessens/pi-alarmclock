package marcclaessens.alarmclock.feature;

import java.util.List;

public interface RssFeedListener {
    void rssLoading(String label);

    void rssContentChanged(List<String> content);
}
