package net.mcl.alarmclock.button;

import java.awt.event.ActionEvent;

import net.mcl.alarmclock.AppContext;

/**
 * Button to go the menu for setting the alarm time.
 */
class AlarmtimeMenuButton extends AbstractIconGlowButton {

    public AlarmtimeMenuButton(AppContext context) {
        super(context, context.icons().getClock());
    }

    @Override
    protected void clicked(ActionEvent event) {
        getContext().screen().setAlarmTimeScene();
    }

}
