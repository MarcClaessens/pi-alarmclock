package marcclaessens.alarmclock.menu;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import marcclaessens.alarmclock.AppContext;
import marcclaessens.alarmclock.button.ButtonType;
import marcclaessens.alarmclock.swing.AppJPanel;

public class BorderPanels {
	private static final String INACTIVE = "inactive";
	private static final String ACTIVE = "active";

	private static final Logger LOGGER = LogManager.getLogger(BorderPanels.class);

	private final AppContext context;
	private final List<JComponent> buttons = new ArrayList<>();
	private final JComponent menuButton;

	private final JComponent left;
	private final JComponent right;
	private final JComponent menu;

	private JPanel toggleContainer;
	private final CardLayout toggleLayout = new CardLayout();

	private final JLabel buttomMessages;

	public BorderPanels(AppContext context) {
		this.context = context;
		this.buttomMessages = new WeatherReportLabel(context);
		loadButtons();
		menuButton = ButtonType.BUTTONMENU.getComponent(context);
		left = getComponentNode(buttons.subList(0, context.props().getButtonLeftCount()));
		right = getComponentNode(buttons.subList(context.props().getButtonLeftCount(), buttons.size()));
		menu = buildMenu();
	}

	private void loadButtons() {
		int buttonscount = context.props().getButtonCount();
		for (int i = 1; i < buttonscount + 1; i++) {
			JComponent button = ButtonType.getClockButton(context, i);
			if (button != null) {
				buttons.add(button);
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
	 * Get the pane with the buttons that need to be shown on the right side. The
	 * number of buttons to show is derived from the number shown on the left.
	 */
	public JComponent getRight() {
		return right;
	}

	private JComponent buildMenu() {
		JPanel panelToggleButton = new AppJPanel(context, new FlowLayout());
		panelToggleButton.add(menuButton);

		toggleContainer = new AppJPanel(context, toggleLayout);

		JPanel panelActive = new AppJPanel(context, new FlowLayout(FlowLayout.CENTER, 5, 10));
		for (ButtonType t : ButtonType.values()) {
			if (t.isAllowedInButtonMenu()) {
				JComponent b = t.getComponent(context);
				panelActive.add(b);
			}
		}

		toggleContainer.add(panelActive, ACTIVE);

		JPanel panelInactive = new AppJPanel(context, new FlowLayout(FlowLayout.CENTER, 10, 10));
		panelInactive.add(buttomMessages);

		toggleContainer.add(panelInactive, INACTIVE);
		toggleLayout.show(toggleContainer, INACTIVE);

		JPanel menu = new AppJPanel(context, new FlowLayout(FlowLayout.LEFT, 10, 5));
		menu.add(panelToggleButton);
		menu.add(toggleContainer);

		return menu;
	}

	public JComponent getMenu() {
		return menu;
	}

	public void setMenuActive(boolean active) {
		toggleLayout.show(toggleContainer, active ? ACTIVE : INACTIVE);
		if (!active) {
			context.screen().setClockScene();
		}
	}

	private JComponent getComponentNode(List<JComponent> buttons) {
		JPanel panel = new AppJPanel(context, new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0, 0, 10, 0);
		c.gridy = 0;
		for (JComponent b : buttons) {
			c.gridy += 1;
			panel.add(b, c);
		}

		return panel;
	}
}
