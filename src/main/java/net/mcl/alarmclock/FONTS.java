package net.mcl.alarmclock;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JComponent;

/**
 * Enum of used Fonts.
 */
public enum FONTS {
    CLOCK(160),
    AWESOME(78),
    MATERIAL_DESIGN(64),
    PLAIN_STANDARD(18),
    PLAIN_LARGE(24),
    INVISIBLE_SPACING(18);

    private int fontsize;

    private FONTS(int fontsize) {
        this.fontsize = fontsize;
    }

    public void applyStyle(JComponent c) {
        switch (this) {
        case AWESOME:
            setFontAndColor(c, "FontAwesome", Color.BLACK, Color.RED);
            break;
        case CLOCK:
            setFontAndColor(c, "Digital-7 Mono", Color.BLACK, Color.RED);
            break;
        case MATERIAL_DESIGN:
            setFontAndColor(c, "Material Design Icons", Color.BLACK, Color.RED);
            break;
        case PLAIN_STANDARD: // fall through
        case PLAIN_LARGE:
            setFontAndColor(c, "Sans-serif", Color.BLACK, Color.RED);
            break;
        case INVISIBLE_SPACING:
            setFontAndColor(c, "Sans-serif", Color.BLACK, Color.BLACK);
            break;
        }
    }

    private void setFontAndColor(JComponent c, String font, Color back, Color front) {
        c.setFont(new Font(font, Font.PLAIN, fontsize));
        c.setBackground(back);
        c.setForeground(front);

    }

    public void alterDefaultSize(int newSize) {
        this.fontsize = newSize;
    }
}
