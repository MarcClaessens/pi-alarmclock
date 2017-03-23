package net.mcl.alarmclock.button;

import javafx.event.ActionEvent;
import net.mcl.alarmclock.AppContext;

class WindowRestoreButton extends AbstractIconGlowButton {
    public WindowRestoreButton(AppContext context) {
        super(context, context.icons().getWindowRestore());
    }

    @Override
    protected void clicked(ActionEvent event) {
        boolean full = getContext().screen().isFullScreen();
        if (!full) {
            getContext().screen().fullScreen();
        } else {
            getContext().screen().exitFullScreen();
        }
    }

}
