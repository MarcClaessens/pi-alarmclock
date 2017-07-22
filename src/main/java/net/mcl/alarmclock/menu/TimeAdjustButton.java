package net.mcl.alarmclock.menu;

import java.awt.event.ActionEvent;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.CharIcon;
import net.mcl.alarmclock.button.AbstractIconGlowButton;

/**
 * Button to adjust the alarm time setting.
 */
class TimeAdjustButton extends AbstractIconGlowButton {
    private final int diff;
    private final boolean up;
    private final boolean hours;
    private final boolean doubleSize;

    public TimeAdjustButton(AppContext context, boolean up, boolean hours, int diff) {
        this(context, up, hours, diff, false);
    }

    public TimeAdjustButton(AppContext context, boolean up, boolean hours, int diff, boolean doubleSize) {
        super(context, context.icons().getArrow());
        setAlignmentX(CENTER_ALIGNMENT);
        this.up = up;
        this.hours = hours;
        this.diff = diff;
        this.doubleSize = doubleSize;
        setIcon(up);
    }

    @Override
    protected void setIcon(boolean on) {
        CharIcon charIcon = getContext().icons().getArrow();
        char c = ((on) ? charIcon.getOnChar() : charIcon.getOffChar());
        String text = (doubleSize) ? "" + c + " " + c : "" + c;
        setText(text);
    }

    @Override
    protected void clicked(ActionEvent event) {
        if (hours) {
            if (up) {
                getContext().alarmClock().plusAlarmTimeHours(diff);
            } else {
                getContext().alarmClock().minusAlarmTimeHours(diff);
            }
        } else {
            if (up) {
                getContext().alarmClock().plusAlarmTimeMinutes(diff);
            } else {
                getContext().alarmClock().minusAlarmTimeMinutes(diff);
            }
        }
    }
}
