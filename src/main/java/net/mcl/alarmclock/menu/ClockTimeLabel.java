package net.mcl.alarmclock.menu;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.AppFonts;
import net.mcl.alarmclock.feature.AlarmTimeListener;
import net.mcl.alarmclock.feature.CurrentTimeListener;
import net.mcl.alarmclock.swing.AppJLabel;

/**
 * Label for showing the time. It is used for both current time and alarm
 * setting.
 */
class ClockTimeLabel extends AppJLabel implements CurrentTimeListener, AlarmTimeListener {
    private final DateTimeFormatter sdf = DateTimeFormatter.ofPattern("HH:mm");

    public ClockTimeLabel(AppContext context) {
        this(context, true);
        context.registerRightClickListener(this);
    }

    public ClockTimeLabel(AppContext context, boolean registerListener) {
        super("00:00", AppFonts.CLOCK);
        setBorder(BorderFactory.createEmptyBorder(10, 0, 40, 0));
        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);

        if (registerListener) {
            context.alarmClock().registerTimeListener(this);
        } else {
            context.alarmClock().registerAlarmListener(this);
            updateAlarmTime(context.alarmClock().getAlarmTime());
        }
    }

    @Override
    public void updateCurrentTime(LocalTime t) {
        setText(sdf.format(t));
    }

    @Override
    public void updateAlarmTime(LocalTime t) {
        setText(sdf.format(t));
    }
}
