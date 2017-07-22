package net.mcl.alarmclock.button;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import net.mcl.alarmclock.AppContext;

/**
 * FX Button with a glowing border. The color is defined in the CSS file.
 */
public abstract class AbstractGlowButton extends JButton {
    private static Color RED1 = new Color(255, 0, 0);
    private static Color RED2 = new Color(180, 0, 0);
    private static Color RED3 = new Color(120, 0, 0);
    private static Color RED4 = new Color(55, 0, 00);

    private final AppContext context;

    protected AbstractGlowButton(AppContext context) {
        super();
        this.context = context;
        setAlignmentX(CENTER_ALIGNMENT);
        setAlignmentY(CENTER_ALIGNMENT);
        glow();
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

    private void glow() {
        CompoundBorder border1 = BorderFactory.createCompoundBorder(new LineBorder(RED4), new LineBorder(RED3));
        CompoundBorder border2 = BorderFactory.createCompoundBorder(new LineBorder(RED1), new LineBorder(RED2));
        CompoundBorder border3 = BorderFactory.createCompoundBorder(border1, border2);
        setBorder(border3);
        setBackground(Color.BLACK);
        setForeground(Color.RED);
    }
}
