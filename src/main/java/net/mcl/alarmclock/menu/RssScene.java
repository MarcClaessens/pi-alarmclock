package net.mcl.alarmclock.menu;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.FONTS;
import net.mcl.alarmclock.feature.RssFeedListener;
import net.mcl.alarmclock.feature.RssSource;

/**
 * Scene for selecting and retrieving RSS content. Note the RSS feed is not
 * automatically refreshed.
 */
public class RssScene extends BlackPanel implements RssFeedListener {
    private static final String SEPARATOR = System.getProperty("line.separator");
    // used to be " /"

    private final JLabel marquee = new JLabel();
    private String marqueeContent = null;

    public RssScene(AppContext context) {
        super(context, new BorderLayout());
        context.rss().registerListener(this);

        add(getTop(), BorderLayout.NORTH);
        add(getCenter(), BorderLayout.CENTER);
        add(getDefaultBottom(), BorderLayout.SOUTH);
    }

    /*
     * the scrolling doesn't work with any pane other than basic ; it also
     * doesn't work if you put said pane into another e.g. Hbox
     */
    private JPanel getCenter() {
        JPanel pane = new BlackPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(marquee);
        FONTS.PLAIN_STANDARD.applyStyle(marquee);

        return pane;
    }

    private JPanel getTop() {
        JPanel pane = new BlackPanel(new GridBagLayout());
        for (RssSource source : getContext().props().getRssSources()) {
            pane.add(new RssChoiceButton(getContext(), source.getLabel(), source.getSourceUrl()));
        }
        return pane;
    }

    @Override
    public void rssLoading(String label) {
        marquee.setText("Loading ...");
    }

    @Override
    public void rssContentChanged(List<String> content) {
        StringBuilder sb = new StringBuilder("<html>");
        sb.append(content.stream().collect(Collectors.joining("<br>")));
        sb.append("</html>");
        marquee.setText(sb.toString());
    }
}
