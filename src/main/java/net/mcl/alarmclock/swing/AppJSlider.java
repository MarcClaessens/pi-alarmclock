package net.mcl.alarmclock.swing;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JSlider;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.event.ChangeListener;

import net.mcl.alarmclock.AppContext;

/**
 * JSlider that takes the background color of the application. It doesn't slide
 * in steps, but immediately jumps to the clicked value.
 */

public class AppJSlider extends JSlider {

    private static final long serialVersionUID = 1L;

    static {
        UIDefaults defaults = UIManager.getDefaults();
        Icon icon = new AppJSliderIcon();
        defaults.put("Slider.horizontalThumbIcon", icon);
        defaults.put("Slider.horizontalSize", new Dimension(250, 60));
    }

    public AppJSlider(String name, int min, int max, AppContext context, ChangeListener listener) {
        super(min, max);
        setName(name);
        setBackground(context.props().getBackGroundColor());
        addChangeListener(listener);
        setUI(new CustomMetalSliderUI());
    }
}
