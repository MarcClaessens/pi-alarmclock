package net.mcl.alarmclock.button;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import net.mcl.alarmclock.AppContext;

/**
 * Button with a glowing border. The color is defined in the CSS file.
 */
public abstract class AbstractGlowButton extends JButton {
    private static final Color RED1 = new Color(255, 0, 0);
    private static final Color RED2 = RED1.darker();
    private static final Color RED3 = RED2.darker().darker();

    private static final Border GLOWBORDER = makeGlowBorder();

    private final AppContext context;

    private static final Color ALMOST_BLACK = Color.BLACK.brighter();

    protected AbstractGlowButton(AppContext context) {
        super();

        this.setContentAreaFilled(false);

        this.context = context;
        setAlignmentX(CENTER_ALIGNMENT);
        setAlignmentY(CENTER_ALIGNMENT);
        setBorder(GLOWBORDER);
        setBackground(Color.BLACK);
        setForeground(Color.RED);
        addActionListener(this::clicked);
    }

    protected abstract void clicked(ActionEvent event);

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    protected AppContext getContext() {
        return context;
    }

    private static Border makeGlowBorder() {
        CompoundBorder border1 = BorderFactory.createCompoundBorder(new LineBorder(RED3, 3, true),
                new LineBorder(RED2, 2, true));
        CompoundBorder border2 = BorderFactory.createCompoundBorder(border1, new LineBorder(RED1, 1, true));
        return border2;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(Color.yellow);
        } else if (getModel().isRollover()) {
            g.setColor(Color.green);
        } else {
            g.setColor(Color.blue);
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    @Override
    public void setContentAreaFilled(boolean b) {
    }
}
