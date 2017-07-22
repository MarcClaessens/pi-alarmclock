package net.mcl.alarmclock.menu;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.FONTS;
import net.mcl.alarmclock.feature.AlarmTimeListener;
import net.mcl.alarmclock.feature.CurrentTimeListener;

/**
 * Label for showing the time. It is used for both current time and alarm
 * setting.
 */
class ClockTimeLabel extends JLabel implements CurrentTimeListener, AlarmTimeListener {
    private final DateTimeFormatter sdf = DateTimeFormatter.ofPattern("HH:mm");

    public ClockTimeLabel(AppContext context) {
        this(context, true);
    }

    public ClockTimeLabel(AppContext context, boolean registerListener) {
        super("00:00");
        FONTS.CLOCK.applyStyle(this);
        setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
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
