package net.mcl.alarmclock.button;

import javafx.event.ActionEvent;
import net.mcl.alarmclock.AppContext;

class AlarmButton extends AbstractIconGlowButton {
    public AlarmButton(AppContext context) {
        super(context, context.icons().getAlarm(), context.alarmClock().isAlarmOn());
    }

    @Override
    protected void clicked(ActionEvent event) {
        getContext().alarmClock().toggleAlarm();
        setIcon(getContext().alarmClock().isAlarmOn());
    }

}
