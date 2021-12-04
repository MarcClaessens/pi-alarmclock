package marcclaessens.alarmclock.button;

import java.awt.event.ActionEvent;

import marcclaessens.alarmclock.AppContext;
import marcclaessens.alarmclock.swing.AbstractIconGlowButton;

public class ColorMenuButton extends AbstractIconGlowButton {
	private static final long serialVersionUID = 1L;

	public ColorMenuButton(AppContext context) {
		super(context, context.icons().getColor());
	}

	@Override
	protected void clicked(ActionEvent event) {
		getContext().screen().setColorScene();
	}
}
