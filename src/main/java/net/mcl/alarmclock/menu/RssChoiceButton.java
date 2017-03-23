package net.mcl.alarmclock.menu;

import javafx.event.ActionEvent;
import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.CSS;
import net.mcl.alarmclock.button.AbstractGlowButton;

class RssChoiceButton extends AbstractGlowButton {
	private final String rssLabel;
	private final String rssSource;
	
	
    public RssChoiceButton(AppContext context, String rssLabel, String rssSource) {
        super(context);
        setText(rssLabel);
        this.rssLabel = rssLabel;
        this.rssSource = rssSource;
        CSS.STANDARD_FONT.applyStyle(this);
    }

    @Override
    protected void clicked(ActionEvent event) {
    	System.out.println("clicked " + rssLabel);
    	getContext().rss().source(rssLabel, rssSource);
    }
}
