package net.mcl.alarmclock.swing;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JSlider;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.event.ChangeListener;

import net.mcl.alarmclock.AppContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * JSlider that takes the background color of the application. It doesn't slide
 * in steps, but immediately jumps to the clicked value.
 */

public class AppJSlider extends JSlider {
    private static final Logger LOGGER = LogManager.getLogger(AppJSlider.class);

    private static final long serialVersionUID = 1L;

    public AppJSlider(String name, int min, int max, AppContext context, ChangeListener listener) {
        super(min, max);
        setName(name);
        setBackground(context.props().getBackGroundColor());
        addChangeListener(listener);
        setUI(new CustomMetalSliderUI());
    }

    public static void configJSlider(AppContext context) {
        UIDefaults defaults = UIManager.getDefaults();
        Icon icon = new AppJSliderIcon(context.props().getSliderIconLength(), context.props().getSliderIconHeight());
        defaults.put("Slider.horizontalThumbIcon", icon);
        Dimension sliderSize = new Dimension(context.props().getSliderLength(), context.props().getSliderHeight());
        defaults.put("Slider.horizontalSize", sliderSize);
        LOGGER.info("sliderSize :" + sliderSize);
    }
}
