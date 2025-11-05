package marcclaessens.alarmclock.button;

import java.awt.event.ActionEvent;

import marcclaessens.alarmclock.AppContext;
import marcclaessens.alarmclock.swing.AbstractIconGlowButton;

/**
 * Power-off button.
 *
 */
class PowerButton extends AbstractIconGlowButton {
	private static final long serialVersionUID = 1L;

	public PowerButton(AppContext context) {
		super(context, context.icons().getPower());
	}

	@Override
	protected void clicked(ActionEvent event) {
		try {

			Process p = new ProcessBuilder().command("sudo", "shutdown", "-h", "now").start();
			p.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
