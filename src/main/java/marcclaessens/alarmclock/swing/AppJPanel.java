package marcclaessens.alarmclock.swing;

import java.awt.LayoutManager;

import javax.swing.JPanel;

import marcclaessens.alarmclock.AppColor;
import marcclaessens.alarmclock.AppContext;

/**
 * JPanel that takes the background color of the application. Registers a
 * right-click listener to quit.
 */
public class AppJPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private AppContext context;

    public AppJPanel(AppContext context) {
        this.context = context;
        setBackground(AppColor.BACKGROUND.getColor());
        context.registerRightClickListener(this);
    }

    public AppJPanel(AppContext context, LayoutManager layout) {
        this(context);
        setLayout(layout);
    }

    public AppContext getContext() {
        return context;
    }
}
