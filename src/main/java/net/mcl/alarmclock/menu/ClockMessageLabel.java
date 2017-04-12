package net.mcl.alarmclock.menu;

import javafx.scene.control.Label;
import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.CSS;
import net.mcl.alarmclock.feature.WeatherReportListener;

/**
 * Label for displaying the weather report.
 */
class ClockMessageLabel extends Label implements WeatherReportListener {

    public ClockMessageLabel(AppContext context) {
        context.weather().registerListener(this);
        CSS.STANDARD_FONT.applyStyle(this);
    }

    @Override
    public void updateWeatherStatus(String string) {
        setText(string);
    }

}
