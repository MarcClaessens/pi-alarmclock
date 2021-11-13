package net.mcl.alarmclock.button;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.AppFonts;
import net.mcl.alarmclock.feature.AlarmTimeListener;
import net.mcl.alarmclock.swing.AbstractGlowButton;
import net.mcl.alarmclock.swing.AbstractIconGlowButton;
import net.mcl.alarmclock.swing.AppJLabel;
import net.mcl.alarmclock.swing.AppJPanel;

/**
 * Button to toggle the alarm on / off.
 */
class AlarmButton extends AppJPanel implements AlarmTimeListener {
	private static final long serialVersionUID = 1L;
	private final DateTimeFormatter sdf = DateTimeFormatter.ofPattern("HH:mm");

	private final AbstractGlowButton button;
	private final AppJLabel label;

	public AlarmButton(AppContext context) {
		super(context, new GridBagLayout());

		label = new AppJLabel("00:00", AppFonts.MICROCLOCK);
		label.setAlignmentX(CENTER_ALIGNMENT);
		updateAlarmTime(context.alarmClock().getAlarmTime());

		button = new AbstractIconGlowButton(context, context.icons().getAlarm(), context.alarmClock().isAlarmOn()) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void clicked(ActionEvent event) {
				getContext().alarmClock().toggleAlarm();
				setIcon(getContext().alarmClock().isAlarmOn());
				label.setVisible(getContext().alarmClock().isAlarmOn());
			}
		};

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridx = 0;
		c.gridy = 0;
		add(button, c);

		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 24;
		add(label, c);

		label.setVisible(getContext().alarmClock().isAlarmOn());

		context.alarmClock().registerAlarmListener(this);
		setBorder(BorderFactory.createEmptyBorder());
	}

	@Override
	public void updateAlarmTime(LocalTime t) {
		label.setText(sdf.format(t) + "\n");
	}

}
