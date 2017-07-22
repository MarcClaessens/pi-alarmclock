package net.mcl.alarmclock.menu;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.button.ButtonType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WingPanels {
    private static final String INACTIVE = "inactive";
    private static final String ACTIVE = "active";

    private static final Logger LOGGER = LogManager.getLogger(WingPanels.class);

    private final AppContext context;
    private final List<JButton> buttons = new ArrayList<>();
    private final JButton menuButton;
    private boolean active = false;

    private final JComponent left;
    private final JComponent right;
    private final JComponent menu;

    private JPanel toggleContainer;
    private final CardLayout toggleLayout = new CardLayout();

    private final JLabel buttomMessages;

    public WingPanels(AppContext context) {
        this.context = context;
        this.buttomMessages = new WeatherReportLabel(context);
        loadButtons();
        menuButton = ButtonType.BUTTONMENU.getButton(context);
        left = getButtonsNode(buttons.subList(0, context.props().getButtonLeftCount()));
        right = getButtonsNode(buttons.subList(context.props().getButtonLeftCount(), buttons.size()));
        menu = buildMenu();
    }

    private void loadButtons() {
        int buttonscount = context.props().getButtonCount();
        for (int i = 1; i < buttonscount + 1; i++) {
            JButton b = ButtonType.getClockButton(context, i);
            if (b != null) {
                buttons.add(b);
            } else {
                LOGGER.warn("Couldn't add clock scene button " + i);
            }
        }
    }

    /**
     * Get the pane with the buttons that need to be shown on the left side. The
     * number of buttons to show on the left side is defined as a property.
     */
    public JComponent getLeft() {
        return left;
    }

    /**
     * Get the pane with the buttons that need to be shown on the right side.
     * The number of buttons to show is derived from the number shown on the
     * left.
     */
    public JComponent getRight() {
        return right;
    }

    private JComponent buildMenu() {
        JPanel panelToggleButton = new BlackPanel(context, new FlowLayout());
        panelToggleButton.add(menuButton);

        toggleContainer = new BlackPanel(context, toggleLayout);

        JPanel panelActive = new BlackPanel(context, new FlowLayout(FlowLayout.CENTER, 10, 10));
        for (ButtonType t : ButtonType.values()) {
            if (t.isAllowedInButtonMenu()) {
                JButton b = t.getButton(context);
                panelActive.add(b);
            }
        }

        toggleContainer.add(panelActive, ACTIVE);

        JPanel panelInactive = new BlackPanel(context, new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelInactive.add(buttomMessages);

        toggleContainer.add(panelInactive, INACTIVE);
        toggleLayout.show(toggleContainer, INACTIVE);

        JPanel menu = new BlackPanel(context, new FlowLayout(FlowLayout.LEFT, 30, 10));
        menu.add(panelToggleButton);
        menu.add(toggleContainer);

        return menu;
    }

    public JComponent getMenu() {
        return menu;
    }

    public void toggleMenu() {
        toggleLayout.show(toggleContainer, active ? INACTIVE : ACTIVE);
        active = !active;
    }

    private JComponent getButtonsNode(List<JButton> buttons) {
        JPanel panel = new BlackPanel(context, new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 0, 10, 0);
        c.gridy = 0;
        for (JButton b : buttons) {
            c.gridy += 1;
            panel.add(b, c);
        }

        return panel;
    }
}
