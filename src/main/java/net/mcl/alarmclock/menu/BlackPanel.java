package net.mcl.alarmclock.menu;

import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import net.mcl.alarmclock.AppContext;

public class BlackPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private AppContext context;

    public BlackPanel(AppContext context) {
        this.context = context;
        setBackground(Color.BLACK);
        context.registerRightClickListener(this);
    }

    public BlackPanel(AppContext context, LayoutManager layout) {
        this(context);
        setLayout(layout);
    }

    public AppContext getContext() {
        return context;
    }
}
