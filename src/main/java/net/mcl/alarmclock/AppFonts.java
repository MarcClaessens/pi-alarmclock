package net.mcl.alarmclock;

import java.awt.Color;
import java.awt.Font;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 * Enum of used Fonts.
 */
public enum AppFonts {
    CLOCK(160, true),
    AWESOME(78, true),
    MATERIAL_DESIGN(64, true),
    PLAIN_STANDARD(18, true),
    PLAIN_LARGE(24, true),
    INVISIBLE_SPACING(18, false);

    private int fontsize;
    private boolean updateColors;
    private Set<JComponent> styledComponents = new HashSet<>();

    private AppFonts(int fontsize, boolean updateColors) {
        this.fontsize = fontsize;
        this.updateColors = updateColors;
    }

    public void applyStyle(JComponent c) {
        styledComponents.add(c);
        switch (this) {
        case AWESOME:
            setFontAndColor(c, "FontAwesome");
            break;
        case CLOCK:
            setFontAndColor(c, "Digital-7 Mono");
            break;
        case MATERIAL_DESIGN:
            setFontAndColor(c, "Material Design Icons");
            break;
        case PLAIN_STANDARD: // fall through
        case PLAIN_LARGE:
            setFontAndColor(c, "Sans-serif");
            break;
        case INVISIBLE_SPACING:
            setInvisibleFont(c, "Sans-serif");
            break;
        }
    }

    public static void refreshStyles() {
        final Color color = AppColor.FOREGROUND.getColor();
        SwingUtilities.invokeLater(() -> {
            for (AppFonts f : values()) {
                for (JComponent c : f.styledComponents) {
                    f.applyStyle(c);
                    if (f.updateColors && c instanceof ColorChangeListener) {
                        ((ColorChangeListener) c).changeForeGround(color);
                    }
                }
            }
        });
    }

    private void setFontAndColor(JComponent c, String font) {
        c.setFont(new Font(font, Font.PLAIN, fontsize));
        c.setBackground(AppColor.BACKGROUND.getColor());
        c.setForeground(AppColor.FOREGROUND.getColor());

    }

    private void setInvisibleFont(JComponent c, String font) {
        setFontAndColor(c, font);
        c.setForeground(AppColor.BACKGROUND.getColor());
    }

    public void alterDefaultSize(int newSize) {
        this.fontsize = newSize;
    }
}
