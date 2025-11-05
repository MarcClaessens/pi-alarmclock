package marcclaessens.alarmclock.menu;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import marcclaessens.alarmclock.AppContext;
import marcclaessens.alarmclock.AppFonts;
import marcclaessens.alarmclock.feature.AlarmTimeListener;
import marcclaessens.alarmclock.feature.CurrentTimeListener;
import marcclaessens.alarmclock.swing.AppJLabel;

/**
 * Label for showing the time. It is used for both current time and alarm
 * setting.
 */
class ClockTimeLabel extends AppJLabel implements CurrentTimeListener, AlarmTimeListener {
	private static final long serialVersionUID = 1L;
	private final DateTimeFormatter sdf = DateTimeFormatter.ofPattern("HH:mm");

	public ClockTimeLabel(AppContext context) {
		this(context, true);
		context.registerRightClickListener(this);
	}

	public ClockTimeLabel(AppContext context, boolean registerListener) {
		super("00:00", AppFonts.CLOCK);
		setBorder(BorderFactory.createEmptyBorder(10, 0, 40, 0));
		setHorizontalAlignment(SwingConstants.CENTER);
		setVerticalAlignment(SwingConstants.CENTER);

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
