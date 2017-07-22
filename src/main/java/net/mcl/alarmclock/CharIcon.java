package net.mcl.alarmclock;

/**
 * Model for a button icon. Css defines the font family to use (e.g.
 * FontAwesome). On and Off icons can be provided as unicode character values.
 * The Off icon is optional.
 */
public class CharIcon {
    private final FONTS css;
    private final char onChar;
    private final char offChar;

    public CharIcon(String config) {
        String[] parts = config.split(",");
        css = FONTS.valueOf(parts[0]);
        onChar = parts[1].charAt(0);
        if (parts.length == 3) {
            offChar = parts[2].charAt(0);
        } else {
            offChar = 0;
        }
    }

    public FONTS getCss() {
        return css;
    }

    public char getOnChar() {
        return onChar;
    }

    public char getOffChar() {
        return offChar;
    }
}
