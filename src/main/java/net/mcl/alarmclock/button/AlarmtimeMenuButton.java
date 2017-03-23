package net.mcl.alarmclock.button;

import javafx.event.ActionEvent;
import net.mcl.alarmclock.AppContext;

class AlarmtimeMenuButton extends AbstractIconGlowButton {

    public AlarmtimeMenuButton(AppContext context) {
        super(context, context.icons().getClock());
    }

    @Override
    protected void clicked(ActionEvent event) {
        getContext().screen().setAlarmTimeScene();
    }

}
