package net.mcl.alarmclock.menu;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.button.ButtonType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Scene for showing the clock. Which buttons to show left or right, is defined
 * in the properties file.
 */
/*
 * Layout
 * 
 * +------------------------------+ | | | | | | | | | B1 | | B3 | | | current
 * time | | | | | | | B2 | | B4 | | | | | | | | |
 * |------------------------------| | weather report |
 * +------------------------------+
 */

public class ClockScene extends BlackPanel {
    private static final Logger LOGGER = LogManager.getLogger(ClockScene.class);

    private final List<JButton> buttons = new ArrayList<>();
    private final JLabel messages;
    private final ClockTimeLabel clock;
    private int rightClicks = 0;

    public ClockScene(AppContext context) {
        super(context, new BorderLayout());
        clock = new ClockTimeLabel(context);
        messages = new ClockMessageLabel(context);

        prepareButtons();

        add(clock, BorderLayout.CENTER);
        add(getLeftButtonsNode(), BorderLayout.WEST);
        add(getRightButtonsNode(), BorderLayout.EAST);
        add(getLabelNode(messages), BorderLayout.SOUTH);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) || SwingUtilities.isMiddleMouseButton(e)) {
                    rightClicks++;
                    if (rightClicks == 4) {
                        System.exit(0);
                    }
                }
            }
        });
    }

    /**
     * Get the pane with the buttons that need to be shown on the left side. The
     * number of buttons to show on the left side is defined as a property.
     */
    private JComponent getLeftButtonsNode() {
        return getButtonsNode(buttons.subList(0, getContext().props().getButtonLeftCount()));
    }

    /**
     * Get the pane with the buttons that need to be shown on the right side.
     * The number of buttons to show is derived from the number shown on the
     * left.
     */
    private JComponent getRightButtonsNode() {
        return getButtonsNode(buttons.subList(getContext().props().getButtonLeftCount(), buttons.size()));
    }

    private void prepareButtons() {
        int buttonscount = getContext().props().getButtonCount();
        for (int i = 1; i < buttonscount + 1; i++) {
            JButton b = ButtonType.getClockButton(getContext(), i);
            if (b != null) {
                buttons.add(b);
            } else {
                LOGGER.warn("Couldn't add clock scene button " + i);
            }
        }
    }

    private JComponent getLabelNode(JLabel label) {
        Box box = Box.createVerticalBox();
        box.add(label);
        return box;
    }

    private JComponent getButtonsNode(List<JButton> buttons) {

        JPanel panel = new BlackPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridy = 0;
        for (JButton b : buttons) {
            c.gridy += 1;
            panel.add(b, c);
        }

        return panel;

    }

}
