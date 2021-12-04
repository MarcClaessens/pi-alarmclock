package marcclaessens.alarmclock.menu;

import java.awt.GridBagLayout;

import marcclaessens.alarmclock.AppContext;
import marcclaessens.alarmclock.swing.AppJPanel;

public class ClockScene extends AppJPanel {
	private static final long serialVersionUID = 1L;
	private final ClockTimeLabel clock;

	public ClockScene(AppContext context) {
		super(context, new GridBagLayout());
		setName("ClockScene");
		clock = new ClockTimeLabel(context);
		add(clock);
	}
}
