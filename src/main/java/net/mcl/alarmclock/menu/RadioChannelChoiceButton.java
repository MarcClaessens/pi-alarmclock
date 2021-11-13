package net.mcl.alarmclock.menu;

import java.awt.event.ActionEvent;
import java.util.List;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.AppFonts;
import net.mcl.alarmclock.feature.RadioChannelSource;
import net.mcl.alarmclock.swing.AbstractGlowButton;

/**
 * Button for selecting (and retrieving) the RSS source.
 */
class RadioChannelChoiceButton extends AbstractGlowButton {
	private static final long serialVersionUID = 1L;
	private final String label;
	private final String url;
	private final List<RadioChannelChoiceButton> allButtons;

	public RadioChannelChoiceButton(AppContext context, RadioChannelSource source,
			List<RadioChannelChoiceButton> allButtons) {
		super(context);

		this.label = source.getLabel();
		this.url = source.getSourceUrl();
		// initialize full width
		setText(" > " + label + " < ");
		this.allButtons = allButtons;
		AppFonts.PLAIN_LARGE.applyStyle(this);
		updateText();
	}

	void updateText() {
		if (getContext().props().getRadioAlarm().equals(url)) {
			setText(" > " + label + " < ");
		} else {
			setText(label);
		}
	}

	@Override
	protected void clicked(ActionEvent event) {
		getContext().alarmClock().changeRadioChannel(url);
		for (RadioChannelChoiceButton b : allButtons) {
			b.updateText();
		}
	}
}
