package net.mcl.alarmclock.menu;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.feature.RadioChannelSource;
import net.mcl.alarmclock.swing.AppJPanel;

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
		List<RadioChannelSource> radioChannels = getContext().props().getRadioChannels();
		JPanel pane = new AppJPanel(getContext(), new GridLayout(radioChannels.size(), 1));
		for (RadioChannelSource source : radioChannels) {
			RadioChannelChoiceButton button = new RadioChannelChoiceButton(getContext(), source, buttons);
			buttons.add(button);
			pane.add(button);
		}
		return pane;
	}
}
