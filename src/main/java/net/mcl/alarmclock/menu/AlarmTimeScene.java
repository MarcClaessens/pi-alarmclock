package net.mcl.alarmclock.menu;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.FONTS;

/**
 * Scene to set the alarm time.
 * 
 * Time can be changed by clicking buttons. There are 2 buttons to add /
 * subtract 1 hour per click. There are 2 buttons to add / subtract 10 minutes
 * per click. There are 2 buttons to add / subtract 1 minute per click.
 * 
 * Changing the minutes can update the hour: 08:00 - 10 minutes becomes 07:50
 * 
 * The time is saved when this scene is exited (see Main). Note that the up/down
 * buttons are likely to become misaligned when changing the font.
 */
public class AlarmTimeScene extends BlackPanel {
    private final ClockTimeLabel clock;

    public AlarmTimeScene(AppContext context) {
        super(context, new BorderLayout());
        this.clock = new ClockTimeLabel(context, false);

        add(getClock(), BorderLayout.CENTER);
        add(getDefaultBottom(), BorderLayout.SOUTH);
    }

    private JPanel getClock() {
        final TimeAdjustButton hoursup = new TimeAdjustButton(getContext(), true, true, 1, true);
        final TimeAdjustButton mins10up = new TimeAdjustButton(getContext(), true, false, 10);
        final TimeAdjustButton mins1up = new TimeAdjustButton(getContext(), true, false, 1);
        final TimeAdjustButton hoursdown = new TimeAdjustButton(getContext(), false, true, 1, true);
        final TimeAdjustButton mins10down = new TimeAdjustButton(getContext(), false, false, 10);
        final TimeAdjustButton mins1down = new TimeAdjustButton(getContext(), false, false, 1);

        final JPanel panel1 = new BlackPanel();
        panel1.add(hoursup);
        panel1.add(blank());
        panel1.add(mins10up);
        panel1.add(mins1up);

        final JPanel panel2 = new BlackPanel();
        panel2.add(hoursdown);
        panel2.add(blank());
        panel2.add(mins10down);
        panel2.add(mins1down);

        final JPanel panel = new BlackPanel(new BorderLayout());
        panel.add(panel1, BorderLayout.NORTH);
        panel.add(clock, BorderLayout.CENTER);
        panel.add(panel2, BorderLayout.SOUTH);

        // create new panel to keep the above components to getter and centered
        final JPanel grouppanel = new BlackPanel(new GridBagLayout());
        grouppanel.add(panel);
        return grouppanel;
    }

    private JComponent blank() {
        JLabel label = new JLabel("ABCD");
        FONTS.INVISIBLE_SPACING.applyStyle(label);
        return label;
    }

}
