package net.mcl.alarmclock.button;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.swing.AbstractIconGlowButton;

public interface ButtonFactory {
    AbstractIconGlowButton getButton(AppContext context);
}
