package net.mcl.alarmclock.button;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.CSS;

/**
 * FX Button with a glowing border. The color is defined in the CSS file.
 */
public abstract class AbstractGlowButton extends Button {
    private final AppContext context;

    protected AbstractGlowButton(AppContext context) {
        super();
        this.context = context;
        CSS.GLOW.applyStyle(this);
        setOnAction(this::clicked);
    }

    protected abstract void clicked(ActionEvent event);

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    protected AppContext getContext() {
        return context;
    }

}
