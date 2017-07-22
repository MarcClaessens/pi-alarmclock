package net.mcl.alarmclock.menu;

import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.button.ButtonType;

public class BlackPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private AppContext context;

    public BlackPanel() {
        setBackground(Color.BLACK);
    }

    public BlackPanel(LayoutManager layout) {
        this();
        setLayout(layout);
    }

    public BlackPanel(AppContext context, LayoutManager layout) {
        this(layout);
        this.context = context;
    }

    public AppContext getContext() {
        return context;
    }

    protected JPanel getDefaultBottom() {
        JPanel box = new BlackPanel();
        box.add(ButtonType.CLOCKMENU.getButton(getContext()));
        box.add(ButtonType.BUTTONMENU.getButton(getContext()));
        return box;
    }
}
