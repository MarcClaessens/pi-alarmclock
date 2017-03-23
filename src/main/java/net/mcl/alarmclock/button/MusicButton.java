package net.mcl.alarmclock.button;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import net.mcl.alarmclock.AppContext;

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
