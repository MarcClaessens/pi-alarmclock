package net.mcl.alarmclock.button;

import javafx.event.ActionEvent;
import net.mcl.alarmclock.AppContext;

class ButtonMenuButton extends AbstractIconGlowButton {

    public ButtonMenuButton(AppContext context) {
        super(context, context.icons().getMenu());
    }

    @Override
    protected void clicked(ActionEvent event) {
    	getContext().screen().setMenuScene();
    }
}
