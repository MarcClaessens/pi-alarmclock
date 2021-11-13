package net.mcl.alarmclock.menu;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.AppFonts;
import net.mcl.alarmclock.feature.WeatherReportListener;
import net.mcl.alarmclock.swing.AppJLabel;

/**
 * Label for displaying the weather report.
 */
class WeatherReportLabel extends AppJLabel implements WeatherReportListener {
	private static final long serialVersionUID = 1L;

	public WeatherReportLabel(AppContext context) {
		super("------", AppFonts.INVISIBLE_SPACING);
		context.weather().registerListener(this);
		setAlignmentX(CENTER_ALIGNMENT);
	}

	@Override
	public void updateWeatherStatus(String string, boolean show) {
		setText(string);
		if (show) {
			AppFonts.PLAIN_LARGE.applyStyle(this);
		} else {
			AppFonts.INVISIBLE_SPACING.applyStyle(this);
		}
	}

}
