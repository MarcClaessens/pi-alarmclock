package net.mcl.alarmclock.button;

import java.awt.event.ActionEvent;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.swing.AbstractIconGlowButton;

/**
 * Button to go to the main menu.
 */
class ButtonMenuButton extends AbstractIconGlowButton {
    private boolean showMenu = false;

    public ButtonMenuButton(AppContext context) {
        super(context, context.icons().getMenu(), true);
    }

    @Override
    protected void clicked(ActionEvent event) {
        showMenu = !showMenu;
        getContext().screen().setMenuPanel(showMenu);
        setIcon(!showMenu);
    }
}
