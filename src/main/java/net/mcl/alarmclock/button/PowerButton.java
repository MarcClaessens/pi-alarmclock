package net.mcl.alarmclock.button;

import java.awt.event.ActionEvent;

import net.mcl.alarmclock.AppContext;

/**
 * Power-off button.
 *
 */
class PowerButton extends AbstractIconGlowButton {

    public PowerButton(AppContext context) {
        super(context, context.icons().getPower());
    }

    @Override
    protected void clicked(ActionEvent event) {
        try {

            Process p = Runtime.getRuntime().exec("sudo shutdown -h now");
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
