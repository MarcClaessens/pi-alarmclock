package net.mcl.alarmclock;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.text.MessageFormat;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.mcl.alarmclock.feature.MainContext;
import net.mcl.alarmclock.menu.AlarmTimeScene;
import net.mcl.alarmclock.menu.BorderPanels;
import net.mcl.alarmclock.menu.ClockScene;
import net.mcl.alarmclock.menu.ColorScene;
import net.mcl.alarmclock.menu.RadioChannelScene;
import net.mcl.alarmclock.menu.RssScene;
import net.mcl.alarmclock.swing.AppJPanel;
import net.mcl.alarmclock.swing.AppJSlider;

/**
 * Main application.
 */
public class Main extends JFrame implements AppScreen {
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LogManager.getLogger(Main.class);

	private final BorderPanels borderPanels;
	private final JPanel root;
	private final JPanel centerPanel;
	private final JPanel bottomPanel;
	private final JPanel leftPanel;
	private final JPanel rightPanel;

	private final ClockScene clockscene;
	private final RssScene rssscene;
	private final AlarmTimeScene alarmtimescene;
	private final ColorScene colorscene;
	private final RadioChannelScene radiochannelscene;

	private final AppContext context;

	private JPanel currentscene;

	public static void main(String[] args) throws Exception {
		try {
			new Main();
		} catch (Exception e) {
			LOGGER.error(e);
			throw e;
		}
	}

	/**
	 * Set application CSS, load Web Fonts and create AppContext instance.
	 */
	public Main() throws Exception {
		try {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

			loadFonts("/fonts/digital-7-(mono).ttf", "/fonts/fa-solid-900.ttf",
					"/fonts/materialdesignicons-webfont.ttf");

			context = new MainContext(this);

			setCustomFontSizes();
			setCustomColors();
			getContentPane().setBackground(AppColor.BACKGROUND.getColor());

			AppJSlider.configJSlider(context);

			borderPanels = new BorderPanels(context);
			root = new AppJPanel(context, new BorderLayout());
			leftPanel = new AppJPanel(context, new GridBagLayout());
			rightPanel = new AppJPanel(context, new GridBagLayout());
			centerPanel = new AppJPanel(context, new CardLayout());
			bottomPanel = new AppJPanel(context, new GridBagLayout());

			createLayoutPanels();

			alarmtimescene = new AlarmTimeScene(context);
			clockscene = new ClockScene(context);
			rssscene = new RssScene(context);
			colorscene = new ColorScene(context);
			radiochannelscene = new RadioChannelScene(context);

			registerScenes(alarmtimescene, clockscene, rssscene, colorscene, radiochannelscene);

			setClockScene();

			setFullScreen();
			setVisible(true);

			LOGGER.info("screen size : " + getContentPane().getWidth() + "-" + getContentPane().getHeight());
			LOGGER.info("centerPanel size : " + centerPanel.getWidth() + "-" + centerPanel.getHeight());
		} catch (Exception e) {
			LOGGER.error(e);
			throw e;
		}
	}

	private void setFullScreen() {
		if (context.props().getFullScreen()) {
			setUndecorated(true);
			GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].setFullScreenWindow(this);
		} else {
			setSize(new Dimension(800, 600));
		}
	}

	private void setCustomColors() {
		AppColor.FOREGROUND.changeDefaultColor(context.props().getForeGroundColor());
		AppColor.BACKGROUND.changeDefaultColor(context.props().getBackGroundColor());
	}

	private void setCustomFontSizes() throws ParseException {
		String sizes = context.props().getCustomFontSizes();
		if (sizes != null) {
			String[] fontsizes = sizes.split(",");
			MessageFormat format = new MessageFormat("{0}({1})");
			for (String source : fontsizes) {
				Object[] parsedResult = format.parse(source);
				AppFonts.valueOf((String) parsedResult[0]).alterDefaultSize(Integer.parseInt((String) parsedResult[1]));
			}
		}
	}

	private void loadFonts(String... resources) throws Exception {
		for (String resource : resources) {
			Font font = Font.createFont(Font.TRUETYPE_FONT, Main.class.getResource(resource).openStream());
			font.deriveFont(Font.PLAIN, 18);
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
			LOGGER.debug("Loaded font : " + font);
		}
	}

	private void createLayoutPanels() {
		getContentPane().add(root);
		root.add(centerPanel, BorderLayout.CENTER);

		leftPanel.add(borderPanels.getLeft());
		rightPanel.add(borderPanels.getRight());
		bottomPanel.add(borderPanels.getMenu());

		root.add(leftPanel, BorderLayout.WEST);
		root.add(rightPanel, BorderLayout.EAST);
		root.add(bottomPanel, BorderLayout.SOUTH);
	}

	private void registerScenes(JPanel... panels) {
		for (JPanel panel : panels) {
			centerPanel.add(panel, panel.getName());
		}
	}

	/**
	 * Change main scene to Clock.
	 */
	@Override
	public void setClockScene() {
		setScene(clockscene);
	}

	/**
	 * Change main scene to AlarmTime.
	 */
	@Override
	public void setAlarmTimeScene() {
		setScene(alarmtimescene);
	}

	/**
	 * Change main scene to Rss.
	 */
	@Override
	public void setRssScene() {
		setScene(rssscene);
	}

	@Override
	public void setRadioChannelScene() {
		setScene(radiochannelscene);
	}

	/**
	 * Change main scene to ColorPicker.
	 */
	@Override
	public void setColorScene() {
		setScene(colorscene);
	}

	/**
	 * Switch scene. If current scene was the AlarmTime, then save the configured
	 * alarm time (as we don't want to save to file on every click).
	 * 
	 * @param scene - the scene to change to.
	 */
	private void setScene(JPanel scene) {
		if (currentscene != null) {
			if (currentscene == alarmtimescene) {
				context.alarmClock().saveAlarmTime();
			}
		}
		currentscene = scene;
		CardLayout cl = (CardLayout) (centerPanel.getLayout());
		cl.show(centerPanel, scene.getName());
	}

	@Override
	public void setMenuPanel(boolean active) {
		borderPanels.setMenuActive(active);
	}

	@Override
	public void setForeGroundColor(Color color) {
		context.props().setForeGroundColor(color);
		setCustomColors();
	}
}
