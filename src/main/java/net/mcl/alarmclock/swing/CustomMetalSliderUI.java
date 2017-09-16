package net.mcl.alarmclock.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalSliderUI;

public class CustomMetalSliderUI extends MetalSliderUI {

    // I wasted a couple of hours to finally solve the sliding issue.
    // Big thanks to "ninesided" for posting his solution on StackOverflow.
    // Original source :
    // https://stackoverflow.com/questions/518471/jslider-question-position-after-leftclick
    // Jason Plank's solution would be even better (as it doesn't depend on the
    // Metal L&F), if it weren't for the paintTrack() that I need to override.
    @Override
    protected void scrollDueToClickInTrack(int direction) {
        int value = slider.getValue();
        value = this.valueForXPosition(slider.getMousePosition().x);
        slider.setValue(value);
    }

    @Override
    public void paintTrack(Graphics g) {
        oceanPaintTrack(g);
    }

    private Rectangle getPaintTrackRect() {
        int trackLeft = 0, trackRight, trackTop = 0, trackBottom;
        trackBottom = (trackRect.height - 1) - getThumbOverhang();
        trackTop = trackBottom - (getTrackWidth() - 1);
        trackRight = trackRect.width - 1;
        return new Rectangle(trackRect.x + trackLeft, trackRect.y + trackTop, trackRight - trackLeft,
                trackBottom - trackTop);
    }

    private void oceanPaintTrack(Graphics g) {
        boolean drawInverted = drawInverted();
        Color sliderAltTrackColor = (Color) UIManager.get("Slider.altTrackColor");

        // Translate to the origin of the painting rectangle
        Rectangle paintRect = getPaintTrackRect();
        g.translate(paintRect.x, paintRect.y);

        // Width and height of the painting rectangle.
        int w = paintRect.width;
        int h = paintRect.height;

        int middleOfThumb = thumbRect.x + thumbRect.width / 2 - paintRect.x;

        int fillMinX;
        int fillMaxX;

        if (middleOfThumb > 0) {
            g.setColor(drawInverted ? MetalLookAndFeel.getControlDarkShadow()
                    : MetalLookAndFeel.getPrimaryControlDarkShadow());

            g.fillRect(0, 0, middleOfThumb - 1, h - 1);
        }

        if (middleOfThumb < w) {
            g.setColor(drawInverted ? MetalLookAndFeel.getPrimaryControlDarkShadow()
                    : MetalLookAndFeel.getControlDarkShadow());

            g.fillRect(middleOfThumb, 0, w - middleOfThumb - 1, h - 1);
        }

        g.setColor(MetalLookAndFeel.getPrimaryControlShadow());
        if (drawInverted) {
            fillMinX = middleOfThumb;
            fillMaxX = w - 2;
            g.drawLine(1, 1, middleOfThumb, 1);
        } else {
            fillMinX = 1;
            fillMaxX = middleOfThumb;
            g.drawLine(middleOfThumb, 1, w - 1, 1);
        }
        if (h == 6) {
            g.setColor(MetalLookAndFeel.getWhite());
            g.drawLine(fillMinX, 1, fillMaxX, 1);
            g.setColor(sliderAltTrackColor);
            g.drawLine(fillMinX, 2, fillMaxX, 2);
            g.setColor(MetalLookAndFeel.getControlShadow());
            g.drawLine(fillMinX, 3, fillMaxX, 3);
            g.setColor(MetalLookAndFeel.getPrimaryControlShadow());
            g.drawLine(fillMinX, 4, fillMaxX, 4);
        }

        g.translate(-paintRect.x, -paintRect.y);
    }
}
