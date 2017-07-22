package net.mcl.alarmclock.button;

import java.awt.event.ActionEvent;

import net.mcl.alarmclock.AppContext;

/**
 * Power-off button.
 *
 */
class ExitButton extends AbstractIconGlowButton {

    public ExitButton(AppContext context) {
        super(context, context.icons().getExit());
    }

    @Override
    protected void clicked(ActionEvent event) {
        try {
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
