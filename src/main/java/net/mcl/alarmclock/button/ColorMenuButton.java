package net.mcl.alarmclock.button;

import java.awt.event.ActionEvent;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.swing.AbstractIconGlowButton;

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
