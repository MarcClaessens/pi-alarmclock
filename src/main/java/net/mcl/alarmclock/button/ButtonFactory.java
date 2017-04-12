package net.mcl.alarmclock.button;

import net.mcl.alarmclock.AppContext;

public interface ButtonFactory {
    AbstractIconGlowButton getButton(AppContext context);
}
