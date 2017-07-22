package net.mcl.alarmclock.menu;

import java.awt.GridBagLayout;

import net.mcl.alarmclock.AppContext;

public class ClockScene extends BlackPanel {
    private final ClockTimeLabel clock;

    public ClockScene(AppContext context) {
        super(context, new GridBagLayout());
        setName("ClockScene");
        clock = new ClockTimeLabel(context);
        add(clock);
    }
}
