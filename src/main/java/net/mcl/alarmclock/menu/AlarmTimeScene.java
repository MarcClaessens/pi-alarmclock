package net.mcl.alarmclock.menu;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.AppFonts;
import net.mcl.alarmclock.swing.AppJLabel;
import net.mcl.alarmclock.swing.AppJPanel;

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
public class AlarmTimeScene extends AppJPanel {
    private final ClockTimeLabel clock;

    public AlarmTimeScene(AppContext context) {
        super(context);
        setName("AlarmTimeScene");
        this.clock = new ClockTimeLabel(context, false);
        add(getClockWithTimeButtons());
    }

    private JPanel getClockWithTimeButtons() {
        final TimeAdjustButton hoursup = new TimeAdjustButton(getContext(), true, true, 1, true);
        final TimeAdjustButton mins10up = new TimeAdjustButton(getContext(), true, false, 10);
        final TimeAdjustButton mins1up = new TimeAdjustButton(getContext(), true, false, 1);
        final TimeAdjustButton hoursdown = new TimeAdjustButton(getContext(), false, true, 1, true);
        final TimeAdjustButton mins10down = new TimeAdjustButton(getContext(), false, false, 10);
        final TimeAdjustButton mins1down = new TimeAdjustButton(getContext(), false, false, 1);

        final JPanel panel1 = new AppJPanel(getContext());
        panel1.add(blank(1));
        panel1.add(hoursup);
        panel1.add(blank(7));
        panel1.add(mins10up);
        panel1.add(blank(1));
        panel1.add(mins1up);

        final JPanel panel2 = new AppJPanel(getContext());
        panel2.add(blank(1));
        panel2.add(hoursdown);
        panel2.add(blank(7));
        panel2.add(mins10down);
        panel2.add(blank(1));
        panel2.add(mins1down);

        final JPanel panel = new AppJPanel(getContext(), new BorderLayout());
        panel.add(panel1, BorderLayout.NORTH);
        panel.add(clock, BorderLayout.CENTER);
        panel.add(panel2, BorderLayout.SOUTH);

        // create new panel to keep the above components to getter and centered
        final JPanel grouppanel = new AppJPanel(getContext(), new GridBagLayout());
        grouppanel.add(panel);
        return grouppanel;
    }

    private JComponent blank(int blanks) {
        JLabel label = new AppJLabel(AppFonts.INVISIBLE_SPACING);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < blanks; i++) {
            sb.append('A');
        }
        label.setText(sb.toString());
        return label;
    }

}
