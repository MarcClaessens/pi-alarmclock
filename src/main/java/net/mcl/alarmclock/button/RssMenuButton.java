package net.mcl.alarmclock.button;

import javafx.event.ActionEvent;
import net.mcl.alarmclock.AppContext;

class RssMenuButton extends AbstractIconGlowButton {

    public RssMenuButton(AppContext context) {
        super(context, context.icons().getRss());
    }

    @Override
    protected void clicked(ActionEvent event) {
    	getContext().screen().setRssScene();
    }
}
