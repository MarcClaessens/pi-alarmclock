package net.mcl.alarmclock.menu;

import javax.swing.JLabel;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.FONTS;
import net.mcl.alarmclock.feature.WeatherReportListener;

/**
 * Label for displaying the weather report.
 */
class WeatherReportLabel extends JLabel implements WeatherReportListener {

    public WeatherReportLabel(AppContext context) {
        context.weather().registerListener(this);
        setText("TEST");
        setAlignmentX(CENTER_ALIGNMENT);
        FONTS.INVISIBLE_SPACING.applyStyle(this);
    }

    @Override
    public void updateWeatherStatus(String string, boolean show) {
        setText(string);
        if (show) {
            FONTS.PLAIN_LARGE.applyStyle(this);
        } else {
            FONTS.INVISIBLE_SPACING.applyStyle(this);
        }
    }

}
