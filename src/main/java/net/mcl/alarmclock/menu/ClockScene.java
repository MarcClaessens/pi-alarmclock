package net.mcl.alarmclock.menu;

import java.awt.GridBagLayout;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.swing.AppJPanel;

public class ClockScene extends AppJPanel {
    private final ClockTimeLabel clock;

    public ClockScene(AppContext context) {
        super(context, new GridBagLayout());
        setName("ClockScene");
        clock = new ClockTimeLabel(context);
        add(clock);
    }
}
