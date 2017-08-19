package net.mcl.alarmclock.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import net.mcl.alarmclock.AppColor;
import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.ColorChangeListener;

/**
 * Button with a glowing border. The color is defined in the CSS file.
 */
public abstract class AbstractGlowButton extends JButton implements ColorChangeListener {

    private final AppContext context;

    protected AbstractGlowButton(AppContext context) {
        super();

        this.setContentAreaFilled(false);

        this.context = context;
        setAlignmentX(CENTER_ALIGNMENT);
        setAlignmentY(CENTER_ALIGNMENT);
        glow(AppColor.FOREGROUND.getColor());
        setBackground(AppColor.BACKGROUND.getColor());
        setForeground(AppColor.FOREGROUND.getColor());
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

    @Override
    public void changeForeGround(Color color) {
        CompoundBorder border1 = BorderFactory.createCompoundBorder(new LineBorder(color.darker().darker(), 3, true),
                new LineBorder(color.darker(), 2, true));
        CompoundBorder border2 = BorderFactory.createCompoundBorder(border1, new LineBorder(color, 1, true));
        setBorder(border2);
    }

    public void glow(Color color) {
        changeForeGround(color);
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
