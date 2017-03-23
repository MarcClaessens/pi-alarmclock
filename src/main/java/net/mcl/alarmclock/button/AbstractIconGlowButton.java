package net.mcl.alarmclock.button;

import javafx.event.ActionEvent;
import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.CharIcon;

public abstract class AbstractIconGlowButton extends AbstractGlowButton{
    private final CharIcon charIcon;

    protected AbstractIconGlowButton(AppContext context, CharIcon charIcon) {
        this(context, charIcon, true);
    }

    protected AbstractIconGlowButton(AppContext context, CharIcon charIcon, boolean useFirstIcon) {
        super(context);
        this.charIcon = charIcon;
        // apply font family
        charIcon.getCss().applyStyle(this);
        setIcon(useFirstIcon);
    }

    protected abstract void clicked(ActionEvent event);

    protected void setIcon(boolean on) {
        setText("" + ((on) ? charIcon.getOnChar() : charIcon.getOffChar()));
    }
}
