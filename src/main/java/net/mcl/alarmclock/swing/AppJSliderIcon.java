package net.mcl.alarmclock.swing;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.SystemColor;

import javax.swing.Icon;

class AppJSliderIcon implements Icon {
    private int width = 16;
    private int height = 36;

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(SystemColor.control);
        g2d.fill3DRect(x + 1, y + 1, width - 2, height - 2, true);
        g2d.dispose();
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }
}
