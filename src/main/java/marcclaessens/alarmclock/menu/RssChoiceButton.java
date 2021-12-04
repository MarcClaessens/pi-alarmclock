package marcclaessens.alarmclock.menu;

import java.awt.event.ActionEvent;

import marcclaessens.alarmclock.AppContext;
import marcclaessens.alarmclock.AppFonts;
import marcclaessens.alarmclock.swing.AbstractGlowButton;

/**
 * Button for selecting (and retrieving) the RSS source.
 */
class RssChoiceButton extends AbstractGlowButton {
	private static final long serialVersionUID = 1L;
	private final String rssLabel;
	private final String rssSource;

	public RssChoiceButton(AppContext context, String rssLabel, String rssSource) {
		super(context);
		setText(rssLabel);
		this.rssLabel = rssLabel;
		this.rssSource = rssSource;
		AppFonts.PLAIN_LARGE.applyStyle(this);
	}

	@Override
	protected void clicked(ActionEvent event) {
		getContext().rss().source(rssLabel, rssSource);
	}
}
