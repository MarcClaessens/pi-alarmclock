package net.mcl.alarmclock.menu;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.AppFonts;
import net.mcl.alarmclock.swing.AbstractGlowButton;
import net.mcl.alarmclock.swing.AppJLabel;
import net.mcl.alarmclock.swing.AppJPanel;
import net.mcl.alarmclock.swing.AppJSlider;

public class ColorScene extends AppJPanel implements ChangeListener {
	private static final long serialVersionUID = 1L;

	private static String[] labels = { "Red", "Green", "Blue", "Alpha" };

	private final JSlider[] sliders = new JSlider[4];
	private final JLabel[] values = new JLabel[4];
	private final AbstractGlowButton apply;
	private Color color = null;

	public ColorScene(AppContext context) {
		super(context, new GridBagLayout());
		setAlignmentX(CENTER_ALIGNMENT);
		GridBagConstraints c = new GridBagConstraints();

		setName("ColorScene");

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10, 0, 25, 0);

		for (int i = 0; i < sliders.length; i++) {
			JLabel label = new AppJLabel(labels[i], AppFonts.PLAIN_LARGE);
			add(label, c);

			JSlider slide = new AppJSlider("slide__" + i, 0, 255, context, this);
			sliders[i] = slide;
			c.gridx++;
			add(slide, c);

			values[i] = new AppJLabel("" + slide.getValue(), AppFonts.PLAIN_LARGE);
			c.gridx++;
			add(values[i], c);
			c.gridx = 0;
			c.gridy++;
		}
		c.gridwidth = 3;

		apply = new AbstractGlowButton(context) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void clicked(ActionEvent event) {
				context.screen().setForeGroundColor(color);
			}
		};
		apply.setText("Apply");
		AppFonts.PLAIN_LARGE.applyStyle(apply);

		JPanel buttonPanel = new AppJPanel(context);
		buttonPanel.add(apply);
		add(buttonPanel, c);
		for (int i = 0; i < sliders.length; i++) {
			sliders[i].setValue(150);
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider slider = (JSlider) e.getSource();
		int index = Integer.parseInt(slider.getName().substring("slide__".length()));
		values[index].setText("" + sliders[index].getValue());
		color = new Color(sliders[0].getValue(), sliders[1].getValue(), sliders[2].getValue(), sliders[3].getValue());
		apply.setForeground(color);
		CompoundBorder border1 = BorderFactory.createCompoundBorder(new LineBorder(color.darker().darker(), 3, true),
				new LineBorder(color.darker(), 2, true));
		CompoundBorder border2 = BorderFactory.createCompoundBorder(border1, new LineBorder(color, 1, true));
		apply.setBorder(border2);
	}
}
