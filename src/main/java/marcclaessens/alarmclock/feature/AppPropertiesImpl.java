package marcclaessens.alarmclock.feature;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.prefs.Preferences;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import marcclaessens.alarmclock.AppProperties;
import marcclaessens.alarmclock.CharIcon;
import marcclaessens.alarmclock.IconProvider;

class AppPropertiesImpl implements AppProperties, IconProvider {
	private static final Logger LOGGER = LogManager.getLogger(AppPropertiesImpl.class);

	private Properties props = null;
	private Preferences prefs = Preferences.userNodeForPackage(getClass());

	public AppPropertiesImpl() {
		props = new Properties();
		File f = new File("app.properties");
		try (InputStream is = new BufferedInputStream(new FileInputStream(f))) {
			props.load(is);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			LOGGER.error(ioe);
		}

		SoundSource.RADIO_CHANNEL.setSource(getRadioAlarm(), getRadioChannelDelayMillis());
		SoundSource.RADIO_ALARM.setSource(getRadioAlarm(), getRadioChannelDelayMillis());
		SoundSource.REPEATING_ALARM.setSource(getLoudAlarm(), getLoudAlarmDelayMillis());
		SoundSource.WHITENOISE.setSource(getWhiteNoiseSource(), getWhiteNoiseDelayMillis());
	}

	private CharIcon charprop(String key) {
		return new CharIcon(props.getProperty(key));
	}

	private String getLoudAlarm() {
		return props.getProperty("alarm.loud.source");
	}

	@Override
	public String getRadioAlarm() {
		return prefs.get("alarm.radio.source", "http://icecast.vrtcdn.be/radio1-high.mp3");
	}

	private String getWhiteNoiseSource() {
		return props.getProperty("whitenoise.source");
	}

	@Override
	public void setRadioAlarm(String newUrl) {
		prefs.put("alarm.radio.source", newUrl);
	}

	@Override
	public List<RadioChannelSource> getRadioChannels() {
		int count = Integer.parseInt(props.getProperty("alarm.radiochannels.count"));
		List<RadioChannelSource> channels = new ArrayList<>();
		for (int i = 1; i < count + 1; i++) {
			String entry[] = props.getProperty("alarm.radiochannel." + i).split(";");
			channels.add(new RadioChannelSource(entry[0], entry[1]));
		}
		return channels;
	}

	@Override
	public boolean getFullScreen() {
		return booleanProp("fullscreen", "true");
	}

	@Override
	public int getButtonCount() {
		return intprop("button.count", "0");
	}

	@Override
	public int getButtonLeftCount() {
		return intprop("button.count.left", "0");
	}

	private int getLoudAlarmDelayMillis() {
		return intprop("alarm.loud.delayMillis", "5000");
	}

	private int getRadioChannelDelayMillis() {
		return intprop("alarm.radiochannels.delaymillis", "250");
	}

	private int getWhiteNoiseDelayMillis() {
		return intprop("whitenoise.delaymillis", "-1");
	}

	@Override
	public int getLoudAlarmActivationDelay() {
		return intprop("alarm.loud.activationdelayminutes", "10");
	}

	@Override
	public String getButtonType(int buttonNr) {
		return props.getProperty("button" + buttonNr, "").toUpperCase();
	}

	@Override
	public CharIcon getAlarm() {
		return charprop("icon.alarm");
	}

	@Override
	public CharIcon getClock() {
		return charprop("icon.clock");
	}

	@Override
	public CharIcon getArrow() {
		return charprop("icon.arrowupdown");
	}

	@Override
	public CharIcon getHome() {
		return charprop("icon.home");
	}

	@Override
	public CharIcon getMenu() {
		return charprop("icon.menu");
	}

	@Override
	public CharIcon getWeather() {
		return charprop("icon.weather");
	}

	@Override
	public CharIcon getMusic() {
		return charprop("icon.music");
	}

	@Override
	public CharIcon getPower() {
		return charprop("icon.power");
	}

	@Override
	public CharIcon getExit() {
		return charprop("icon.exit");
	}

	@Override
	public String getWeatherSource() {
		return props.getProperty("weather.source");
	}

	private int intprop(String key, String defaultValue) {
		return Integer.parseInt(props.getProperty(key, defaultValue));
	}

	private boolean booleanProp(String key, String defaultValue) {
		return Boolean.parseBoolean(props.getProperty(key, defaultValue));
	}

	@Override
	public CharIcon getRss() {
		return charprop("icon.rss");
	}

	@Override
	public CharIcon getColor() {
		return charprop("icon.colors");
	}

	@Override
	public CharIcon getRadioChannel() {
		return charprop("icon.radiochannel");
	}

	@Override
	public CharIcon getWhiteNoise() {
		return charprop("icon.whitenoise");
	}

	@Override
	public List<RssSource> getRssSources() {
		List<RssSource> rss = new ArrayList<>();
		int count = Integer.parseInt(props.getProperty("rss.count", "0"));
		for (int i = 1; i <= count; i++) {
			String[] parts = props.getProperty("rss.source" + i).split(",");
			rss.add(new RssSource(parts[0], parts[1]));
		}
		return rss;
	}

	@Override
	public int getRssFetchCount() {
		return intprop("rss.fetchcount", "10");
	}

	@Override
	public String getCustomFontSizes() {
		return props.getProperty("font.size");
	}

	@Override
	public void setAlarmTime(LocalTime time) {
		prefs.putInt("alarm.hour", time.getHour());
		prefs.putInt("alarm.minutes", time.getMinute());
	}

	@Override
	public LocalTime getAlarmTime() {
		int h = prefs.getInt("alarm.hour", 8);
		int m = prefs.getInt("alarm.minutes", 0);
		return LocalTime.of(h, m);
	}

	@Override
	public void setForeGroundColor(Color color) {
		saveColor("color.front", color);
	}

	@Override
	public Color getForeGroundColor() {
		return getColor("color.front", Color.RED);
	}

	@Override
	public void setBackGroundColor(Color color) {
		saveColor("color.back", color);
	}

	@Override
	public Color getBackGroundColor() {
		return getColor("color.back", Color.BLACK);
	}

	@Override
	public int getSliderHeight() {
		return intprop("slider.bar.height", "60");
	}

	@Override
	public int getSliderLength() {
		return intprop("slider.bar.length", "160");
	}

	@Override
	public int getSliderIconHeight() {
		return intprop("slider.icon.height", "36");
	}

	@Override
	public int getSliderIconLength() {
		return intprop("slider.icon.width", "16");
	}

	private void saveColor(String key, Color c) {
		prefs.putInt(key + ".red", c.getRed());
		prefs.putInt(key + ".green", c.getGreen());
		prefs.putInt(key + ".blue", c.getBlue());
		prefs.putInt(key + ".alfa", c.getAlpha());
	}

	private Color getColor(String key, Color defaultColor) {
		int r = prefs.getInt(key + ".red", defaultColor.getRed());
		int g = prefs.getInt(key + ".green", defaultColor.getGreen());
		int b = prefs.getInt(key + ".blue", defaultColor.getBlue());
		int a = prefs.getInt(key + ".alfa", defaultColor.getAlpha());
		return new Color(r, g, b, a);
	}

	@Override
	public String getMixer() {
		return props.getProperty("audio.mixer", "");
	}
}
