package net.mcl.alarmclock.button;

import net.mcl.alarmclock.AppContext;

public enum ButtonType implements ButtonFactory {
    BUTTONMENU(true, false, c -> {return new ButtonMenuButton(c);} ),
    CLOCKMENU(false, true, c -> {return new ClockMenuButton(c);}),
    ALARMTIMEMENU(true, true, c -> {return new AlarmtimeMenuButton(c);}),
    RSSMENU(true, true, c -> {return new RssMenuButton(c);}),

    ALARM(true, false, c -> {return new AlarmButton(c);}),
    WEATHER(true, true, c -> {return new WeatherButton(c);}),
    MUSIC(true, true, c -> {return new MusicButton(c);}),
    
    
    POWER(false, true, c -> {return new PowerButton(c);}),
    WINDOWRESTORE(false, true, c -> {return new WindowRestoreButton(c);});

    private boolean allowedInClock;
    private boolean allowedInButtonMenu;
    private ButtonFactory buttonProvider;

    private ButtonType(boolean allowedInClock, boolean allowedInButtonMenu, ButtonFactory provider) {
        this.allowedInClock = allowedInClock;
        this.allowedInButtonMenu = allowedInButtonMenu;
        this.buttonProvider = provider;
    }
    
    public boolean isAllowedInButtonMenu() {
        return allowedInButtonMenu;
    }

    public boolean isAllowedInClock() {
        return allowedInClock;
    }

	@Override
	public AbstractIconGlowButton getButton(AppContext context) {
		return buttonProvider.getButton(context);
	}
	
    public static AbstractIconGlowButton getClockButton(AppContext context, int buttonNumber) {
        ButtonType buttonType;
        String code = context.props().getButtonType(buttonNumber);
        if (code == null || code.length() == 0) {
            buttonType = null;
        } else {
            buttonType = ButtonType.valueOf(code);
        }

        if (buttonType != null) {
            if (buttonType.isAllowedInClock()) {
                return buttonType.getButton(context);
            } else {
                System.err.println("The " + buttonType + " menu button is not allowed in the clock panel.");
            }
        }
        return null;
    }
}
