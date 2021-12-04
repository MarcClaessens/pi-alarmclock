package marcclaessens.alarmclock.button;

import marcclaessens.alarmclock.AppContext;
import marcclaessens.alarmclock.swing.AbstractIconGlowButton;

public interface ButtonFactory {
    AbstractIconGlowButton getButton(AppContext context);
}
