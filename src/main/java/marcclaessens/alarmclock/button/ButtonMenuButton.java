package marcclaessens.alarmclock.button;

import java.awt.event.ActionEvent;

import marcclaessens.alarmclock.AppContext;
import marcclaessens.alarmclock.swing.AbstractIconGlowButton;

/**
 * Button to go to the main menu.
 */
class ButtonMenuButton extends AbstractIconGlowButton {
	private static final long serialVersionUID = 1L;
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
