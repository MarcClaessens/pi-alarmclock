package net.mcl.alarmclock.menu;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.button.ButtonType;

/**
 * Scene for showing all menu buttons.
 */
public class MenuScene extends BlackPanel {

    public MenuScene(AppContext context) {
        super(context, new GridBagLayout());
        prepareButtons();
    }

    private void prepareButtons() {
        JPanel panel = new BlackPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        for (ButtonType t : ButtonType.values()) {
            if (t.isAllowedInButtonMenu()) {
                JButton b = t.getButton(getContext());
                panel.add(b);
            }
        }
        add(panel);
    }
}
