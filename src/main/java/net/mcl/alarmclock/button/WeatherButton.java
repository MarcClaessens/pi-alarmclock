package net.mcl.alarmclock.button;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import net.mcl.alarmclock.AppContext;

/**
 * Button to toggle reporting the weather report.
 */
class WeatherButton extends AbstractIconGlowButton {
    private static final List<WeatherButton> instances = new ArrayList<>();

    public WeatherButton(AppContext context) {
        super(context, context.icons().getWeather(), context.weather().isWeatherReportOn());
        instances.add(this);
    }

    @Override
    protected void clicked(ActionEvent event) {
        getContext().weather().toggleWeatherReportOn();
        // synchronize with other instances of this button
        for (WeatherButton b : instances) {
            b.setIcon(getContext().weather().isWeatherReportOn());
        }
    }
}
