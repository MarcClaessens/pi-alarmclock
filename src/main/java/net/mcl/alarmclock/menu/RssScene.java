package net.mcl.alarmclock.menu;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import java.util.stream.Collectors;

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
    private final JLabel marquee = new JLabel();

    private final JPanel top;

    public RssScene(AppContext context) {
        super(context);
        setName("RssScene");
        FONTS.PLAIN_STANDARD.applyStyle(marquee);

        top = getTop();
        add(top);
        add(marquee);

        context.registerRightClickListener(marquee);
        context.rss().registerListener(this);
    }

    private JPanel getTop() {
        JPanel pane = new BlackPanel(getContext(), new FlowLayout(FlowLayout.CENTER, 10, 0));
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
        String text = content.stream().collect(Collectors.joining("<br>"));
        marquee.setPreferredSize(new Dimension(this.getWidth() - 20, this.getHeight() - top.getHeight() - 10));
        marquee.setSize(new Dimension(this.getWidth() - 20, this.getHeight() - top.getHeight() - 10));
        marquee.setText("<html>" + text + "<html>");
        marquee.setAlignmentX(LEFT_ALIGNMENT);
    }
}
