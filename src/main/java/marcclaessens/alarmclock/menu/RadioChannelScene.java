package marcclaessens.alarmclock.menu;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import marcclaessens.alarmclock.AppContext;
import marcclaessens.alarmclock.feature.RadioChannel;
import marcclaessens.alarmclock.swing.AppJPanel;

/**
 * Scene for selecting and retrieving RSS content. Note the RSS feed is not
 * automatically refreshed.
 */
public class RadioChannelScene extends AppJPanel {
	private static final long serialVersionUID = 1L;
	private final JPanel panel;
	private List<RadioChannelChoiceButton> buttons = new ArrayList<>();

	public RadioChannelScene(AppContext context) {
		super(context, new GridBagLayout());

		setName("RadioChannelScene");

		panel = getTop();
		add(panel);
	}

	private JPanel getTop() {
		List<RadioChannel> radioChannels = getContext().props().getRadioChannels();
		JPanel pane = new AppJPanel(getContext(), new GridLayout(radioChannels.size(), 1));
		for (RadioChannel channel: radioChannels) {
			RadioChannelChoiceButton button = new RadioChannelChoiceButton(getContext(), channel, buttons);
			buttons.add(button);
			pane.add(button);
		}
		return pane;
	}
}
