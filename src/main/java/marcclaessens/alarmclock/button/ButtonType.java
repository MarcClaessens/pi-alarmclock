package marcclaessens.alarmclock.button;

import javax.swing.JComponent;

import org.apache.logging.log4j.LogManager;

import marcclaessens.alarmclock.AppContext;

/**
 * Enum and factory of all buttons. Defines also whether the button is allowed
 * in the Clock Scene or the main Button Menu.
 *
 */
public enum ButtonType {
	BUTTONMENU(false, false, c -> new ButtonMenuButton(c)), CLOCKMENU(false, true, c -> new ClockMenuButton(c)),
	ALARMTIMEMENU(true, true, c -> new AlarmtimeMenuButton(c)), RSSMENU(true, true, c -> new RssMenuButton(c)),
	COLORMENU(true, true, c -> new ColorMenuButton(c)), ALARM(true, false, c -> new AlarmButton(c)),
	WEATHER(true, false, c -> new WeatherButton(c)), MUSIC(true, false, c -> new MusicButton(c)),
	WHITENOISE(true, false, c -> new WhiteNoiseButton(c)),
	MUSIC_CHOICE(false, true, c -> new RadioChannelMenuButton(c)), POWER(true, true, c -> new PowerButton(c)),
	EXIT(true, true, c -> new ExitButton(c));

	static interface ComponentFactory {
		JComponent getComponent(AppContext context);
	}

	private boolean allowedInClock;
	private boolean allowedInButtonMenu;
	private ComponentFactory factoryImpl;

	private ButtonType(boolean allowedInClock, boolean allowedInButtonMenu, ComponentFactory factoryImpl) {
		this.allowedInClock = allowedInClock;
		this.allowedInButtonMenu = allowedInButtonMenu;
		this.factoryImpl = factoryImpl;
	}

	public boolean isAllowedInButtonMenu() {
		return allowedInButtonMenu;
	}

	public boolean isAllowedInClock() {
		return allowedInClock;
	}

	public JComponent getComponent(AppContext context) {
		return factoryImpl.getComponent(context);
	}

	/**
	 * Returns a button based on app.properties config.
	 * 
	 * @param context      - the AppContext from Main
	 * @param buttonNumber - the config number e.g. "3" for configuration line
	 *                     "button3=MUSIC"
	 * @return the matching button instance.
	 */
	public static JComponent getClockButton(AppContext context, int buttonNumber) {
		ButtonType buttonType;
		String code = context.props().getButtonType(buttonNumber);
		if (code == null || code.length() == 0) {
			buttonType = null;
		} else {
			buttonType = ButtonType.valueOf(code);
		}

		if (buttonType != null) {
			if (buttonType.isAllowedInClock()) {
				return buttonType.getComponent(context);
			} else {
				LogManager.getLogger(ButtonType.class)
						.warn("The " + buttonType + " menu button is not allowed in the clock panel.");
			}
		}
		return null;
	}
}
