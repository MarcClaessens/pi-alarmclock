package marcclaessens.alarmclock;

import java.awt.Font;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComponent;

/**
 * Enum of used Fonts.
 */
public enum AppFonts {
	CLOCK(160), MICROCLOCK(48), AWESOME(48), MATERIAL_DESIGN(64), PLAIN_STANDARD(18), PLAIN_LARGE(24),
	INVISIBLE_SPACING(18);

	private int fontsize;
	private Set<JComponent> styledComponents = new HashSet<>();

	private AppFonts(int fontsize) {
		this.fontsize = fontsize;
	}

	public void applyStyle(JComponent c) {
		styledComponents.add(c);
		switch (this) {
		case AWESOME:
			setFontAndColor(c, "Font Awesome 5 Free Solid");
			break;
		case CLOCK: // fall through
		case MICROCLOCK:
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
		default:
			setFontAndColor(c, "Sans-serif");
		}
	}

	private void setFontAndColor(JComponent c, String font) {
		c.setFont(new Font(font, Font.PLAIN, fontsize));
		c.setBackground(AppColor.BACKGROUND.getColor());
		c.setForeground(AppColor.FOREGROUND.getColor());
		if (c instanceof ColorChangeListener) {
			AppColor.registerListener((ColorChangeListener) c);
		}

	}

	private void setInvisibleFont(JComponent c, String font) {
		c.setFont(new Font(font, Font.PLAIN, fontsize));
		c.setForeground(AppColor.BACKGROUND.getColor());
	}

	public void alterDefaultSize(int newSize) {
		this.fontsize = newSize;
	}
}
