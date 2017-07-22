package net.mcl.alarmclock.menu;

import java.awt.event.ActionEvent;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.FONTS;
import net.mcl.alarmclock.button.AbstractGlowButton;

/**
 * Button for selecting (and retrieving) the RSS source.
 */
class RssChoiceButton extends AbstractGlowButton {
    private final String rssLabel;
    private final String rssSource;

    public RssChoiceButton(AppContext context, String rssLabel, String rssSource) {
        super(context);
        setText(rssLabel);
        this.rssLabel = rssLabel;
        this.rssSource = rssSource;
        FONTS.PLAIN_STANDARD.applyStyle(this);
    }

    @Override
    protected void clicked(ActionEvent event) {
        getContext().rss().source(rssLabel, rssSource);
    }
}
