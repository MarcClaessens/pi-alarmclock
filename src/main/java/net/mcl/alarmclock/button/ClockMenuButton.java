package net.mcl.alarmclock.button;

import javafx.event.ActionEvent;
import net.mcl.alarmclock.AppContext;

class ClockMenuButton extends AbstractIconGlowButton {

    public ClockMenuButton(AppContext context) {
        super(context, context.icons().getHome());
    }

    @Override
    protected void clicked(ActionEvent event) {
    	getContext().screen().setClockScene();
    }

}
