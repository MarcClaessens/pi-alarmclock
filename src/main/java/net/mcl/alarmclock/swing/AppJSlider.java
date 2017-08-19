package net.mcl.alarmclock.swing;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;

import net.mcl.alarmclock.AppContext;

/**
 * JSlider that takes the background color of the application. It doesn't slide
 * in steps, but immediately jumps to the clicked value.
 */
// I wasted a couple of hours to finally solve the sliding issue.
// Big thanks to Jason Plank for posting his solution on StackOverflow.
// Original source :
// https://stackoverflow.com/questions/518471/jslider-question-position-after-leftclick

public class AppJSlider extends JSlider {
    public AppJSlider(String name, int min, int max, AppContext context, ChangeListener listener) {
        super(min, max);
        setName(name);
        setBackground(context.props().getBackGroundColor());
        addChangeListener(listener);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        MouseListener[] listeners = getMouseListeners();
        for (MouseListener l : listeners) {
            removeMouseListener(l); // remove UI-installed TrackListener
        }
        final BasicSliderUI ui = (BasicSliderUI) getUI();
        BasicSliderUI.TrackListener tl = ui.new TrackListener() {
            // this is where we jump to absolute value of click
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                int value = ui.valueForXPosition(p.x);
                setValue(value);
            }

            // disable check that will invoke
            // scrollDueToClickInTrack
            @Override
            public boolean shouldScroll(int dir) {
                return false;
            }
        };
        addMouseListener(tl);
    }
}
