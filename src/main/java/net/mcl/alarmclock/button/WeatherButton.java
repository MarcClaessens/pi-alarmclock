package net.mcl.alarmclock.button;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.swing.AbstractIconGlowButton;

/**
 * Button to toggle reporting the weather report.
 */
class WeatherButton extends AbstractIconGlowButton {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(WeatherButton.class);

	private static final List<WeatherButton> instances = new ArrayList<>();

	public WeatherButton(AppContext context) {
		super(context, context.icons().getWeather(), context.weather().isWeatherReportOn());
		instances.add(this);
	}

	@Override
	protected void clicked(ActionEvent event) {
		LOGGER.warn("WeatherButton Event " + event.getSource() + " / " + event.getActionCommand());
		getContext().weather().toggleWeatherReportOn();
		// synchronize with other instances of this button
		for (WeatherButton b : instances) {
			b.setIcon(getContext().weather().isWeatherReportOn());
		}
	}
}
