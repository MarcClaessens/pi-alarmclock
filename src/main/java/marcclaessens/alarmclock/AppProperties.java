package marcclaessens.alarmclock;

import java.awt.Color;
import java.time.LocalTime;
import java.util.List;

import marcclaessens.alarmclock.feature.RadioChannel;
import marcclaessens.alarmclock.feature.RssSource;
import marcclaessens.alarmclock.sound.SoundSourceType;

public interface AppProperties {
	int getLoudAlarmActivationDelay();

	boolean getFullScreen();

	int getButtonCount();

	int getButtonLeftCount();

	String getButtonType(int buttonNr);

	String getWeatherSource();

	List<RssSource> getRssSources();

	int getRssFetchCount();

	String getCustomFontSizes();

	/**
	 * Sets the alarm time in HH:MM
	 */
	void setAlarmTime(LocalTime time);

	/**
	 * Gets the alarm time in HH:MM (array element 0 is hours, array element 1 is
	 */
	LocalTime getAlarmTime();

	void setForeGroundColor(Color color);

	Color getForeGroundColor();

	void setBackGroundColor(Color color);

	Color getBackGroundColor();

	int getSliderLength();

	int getSliderHeight();

	int getSliderIconLength();

	int getSliderIconHeight();

	List<RadioChannel> getRadioChannels();

	void setRadioAlarm(int radioChannelIndex);

	String getMixer();

	int getSoundSourceDelay(SoundSourceType type);
}
