package marcclaessens.alarmclock.button;

import java.awt.event.ActionEvent;

import marcclaessens.alarmclock.AppContext;
import marcclaessens.alarmclock.swing.AbstractIconGlowButton;

/**
 * Power-off button.
 *
 */
class ExitButton extends AbstractIconGlowButton {
	private static final long serialVersionUID = 1L;

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
