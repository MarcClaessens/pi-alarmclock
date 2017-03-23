package net.mcl.alarmclock.button;

import javafx.event.ActionEvent;
import net.mcl.alarmclock.AppContext;

class PowerButton extends AbstractIconGlowButton {

    public PowerButton(AppContext context) {
        super(context, context.icons().getPower());
    }

    @Override
    protected void clicked(ActionEvent event) {
        try {
            /*
             * Process p = Runtime.getRuntime().exec("sudo shutdown -h now");
             * p.waitFor();
             */
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
