package marcclaessens.alarmclock.menu;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JLabel;
import javax.swing.JPanel;

import marcclaessens.alarmclock.AppContext;
import marcclaessens.alarmclock.AppFonts;
import marcclaessens.alarmclock.feature.RssFeedListener;
import marcclaessens.alarmclock.feature.RssSource;
import marcclaessens.alarmclock.swing.AppJLabel;
import marcclaessens.alarmclock.swing.AppJPanel;

/**
 * Scene for selecting and retrieving RSS content. Note the RSS feed is not
 * automatically refreshed.
 */
public class RssScene extends AppJPanel implements RssFeedListener {
	private static final long serialVersionUID = 1L;
	private final JLabel marquee = new AppJLabel(AppFonts.PLAIN_STANDARD);

	private final JPanel top;

	public RssScene(AppContext context) {
		super(context);
		setName("RssScene");

		top = getTop();
		add(top);
		add(marquee);

		context.registerRightClickListener(marquee);
		context.rss().registerListener(this);
	}

	private JPanel getTop() {
		JPanel pane = new AppJPanel(getContext(), new FlowLayout(FlowLayout.CENTER, 10, 0));
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
