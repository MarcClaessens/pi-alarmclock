package marcclaessens.alarmclock.swing;

import marcclaessens.alarmclock.AppContext;
import marcclaessens.alarmclock.CharIcon;

/**
 * Button with a glowing border and an 1 character "icon" from the defined web
 * fonts. The color is defined in the CSS file.
 */
public abstract class AbstractIconGlowButton extends AbstractGlowButton {
	private static final long serialVersionUID = 1L;

	private final CharIcon charIcon;

	/**
	 * Constructor.
	 * 
	 * @param context  - the application context (from Main)
	 * @param charIcon - the CharacterIcon to use as text
	 */
	protected AbstractIconGlowButton(AppContext context, CharIcon charIcon) {
		this(context, charIcon, true);
	}

	/**
	 * Constructor.
	 * 
	 * @param context      - the application context (from Main)
	 * @param charIcon     - the CharacterIcon to use as text
	 * @param useFirstIcon - use the "On" state or "Off" state of the charIcon.
	 */
	protected AbstractIconGlowButton(AppContext context, CharIcon charIcon, boolean useFirstIcon) {
		super(context);
		this.charIcon = charIcon;
		// apply font family
		charIcon.getCss().applyStyle(this);
		setIcon(useFirstIcon);
	}

	protected CharIcon getCharIcon() {
		return charIcon;
	}

	protected void setIcon(boolean on) {
		setText("" + ((on) ? charIcon.getOnChar() : charIcon.getOffChar()));
	}
}
