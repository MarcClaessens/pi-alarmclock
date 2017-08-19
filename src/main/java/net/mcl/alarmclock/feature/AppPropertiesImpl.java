package net.mcl.alarmclock.feature;

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

import net.mcl.alarmclock.AppProperties;
import net.mcl.alarmclock.CharIcon;
import net.mcl.alarmclock.IconProvider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    }

    private CharIcon charprop(String key) {
        return new CharIcon(props.getProperty(key));
    }

    @Override
    public String getLoudAlarm() {
        return props.getProperty("alarm.loud.source");
    }

    @Override
    public String getRadioAlarm() {
        return props.getProperty("alarm.radio.source");
    }

    @Override
    public int getButtonCount() {
        return intprop("button.count", "0");
    }

    @Override
    public int getButtonLeftCount() {
        return intprop("button.count.left", "0");
    }

    @Override
    public int getLoudAlarmRepeatDelay() {
        return intprop("alarm.loud.repeatdelayseconds", "10");
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

    @Override
    public CharIcon getRss() {
        return charprop("icon.rss");
    }

    @Override
    public CharIcon getColor() {
        return charprop("icon.colors");
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
}
