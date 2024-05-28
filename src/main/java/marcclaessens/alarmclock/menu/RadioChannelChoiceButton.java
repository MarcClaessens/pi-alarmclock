package marcclaessens.alarmclock.menu;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Optional;

import marcclaessens.alarmclock.AppContext;
import marcclaessens.alarmclock.AppFonts;
import marcclaessens.alarmclock.feature.RadioChannel;
import marcclaessens.alarmclock.swing.AbstractGlowButton;

/**
 * Button for selecting (and retrieving) the RSS source.
 */
class RadioChannelChoiceButton extends AbstractGlowButton {
	private static final long serialVersionUID = 1L;
	private final String label;
	private final int index;
	private final List<RadioChannelChoiceButton> allButtons;

	public RadioChannelChoiceButton(AppContext context, RadioChannel source,
			List<RadioChannelChoiceButton> allButtons) {
		super(context);

		this.label = source.getLabel();
		this.index = source.getIndex();
		// initialize full width
		setText(" > " + label + " < ");
		this.allButtons = allButtons;
		AppFonts.PLAIN_LARGE.applyStyle(this);

		int defaultSelected = 1;
		Optional<RadioChannel> optChannel = context.props().getRadioChannels().stream()
				.filter(channel -> channel.isDefaultSelection()).findFirst();
		if (optChannel.isPresent()) {
			defaultSelected = optChannel.get().getIndex();
		}
		updateText(defaultSelected);
	}

	void updateText(int changedIndex) {
		if (index == changedIndex) {
			setText(" > " + label + " < ");
		} else {
			setText(label);
		}
	}

	@Override
	protected void clicked(ActionEvent event) {
		final RadioChannelChoiceButton button = (RadioChannelChoiceButton) event.getSource();
		getContext().alarmClock().changeRadioChannel(button.index);

		getContext().props().getRadioChannels()
				.forEach(channel -> channel.setDefaultSelection(channel.getIndex() == button.index));

		for (RadioChannelChoiceButton b : allButtons) {
			b.updateText(button.index);
		}
	}
}
