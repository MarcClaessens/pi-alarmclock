package net.mcl.alarmclock.button;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import net.mcl.alarmclock.AppContext;

/**
 * Button to toggle playing music.
 */
class MusicButton extends AbstractIconGlowButton {
    private static final List<MusicButton> instances = new ArrayList<>();

    public MusicButton(AppContext context) {
        super(context, context.icons().getMusic(), false);
        instances.add(this);
    }

    @Override
    protected void clicked(ActionEvent event) {
        getContext().alarmClock().toggleMusic();
        for (MusicButton b : instances) {
            b.setIcon(getContext().alarmClock().isMusicPlaying());
        }
    }

}
