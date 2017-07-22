package net.mcl.alarmclock;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.text.MessageFormat;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.mcl.alarmclock.feature.MainContext;
import net.mcl.alarmclock.menu.AlarmTimeScene;
import net.mcl.alarmclock.menu.BlackPanel;
import net.mcl.alarmclock.menu.ClockScene;
import net.mcl.alarmclock.menu.RssScene;
import net.mcl.alarmclock.menu.WingPanels;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main application.
 */
public class Main extends JFrame implements AppScreen {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    private final WingPanels sideWings;
    private final JPanel root;
    private final JPanel topPanel;
    private final JPanel centerPanel;
    private final JPanel bottomPanel;
    private final JPanel leftPanel;
    private final JPanel rightPanel;

    private final JPanel clockscene;
    private final JPanel rssscene;
    private final JPanel alarmtimescene;

    private final AppContext context;

    private JPanel currentscene;

    public static void main(String[] args) throws Exception {
        try {

            Main app = new Main();

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
            getContentPane().setBackground(Color.black);
            getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

            loadFonts("/fonts/digital-7-(mono).ttf", "/fonts/fontawesome-webfont.ttf",
                    "/fonts/materialdesignicons-webfont.ttf");

            context = new MainContext(this);
            setCustomFontSizes();

            sideWings = new WingPanels(context);
            root = new BlackPanel(context, new BorderLayout());
            leftPanel = new BlackPanel(context, new GridBagLayout());
            rightPanel = new BlackPanel(context, new GridBagLayout());
            topPanel = new BlackPanel(context, new FlowLayout(FlowLayout.LEFT));
            centerPanel = new BlackPanel(context, new CardLayout());
            bottomPanel = new BlackPanel(context, new GridBagLayout());

            createLayoutPanels();

            alarmtimescene = new AlarmTimeScene(context);
            clockscene = new ClockScene(context);
            rssscene = new RssScene(context);

            registerScenes(alarmtimescene, clockscene, rssscene);

            setClockScene();

            setFullScreen();
            setVisible(true);

        } catch (Exception e) {
            LOGGER.error(e);
            throw e;
        }
    }

    private void setFullScreen() {
        if (System.getenv().get("dev") != null) {
            setSize(new Dimension(800, 600));
        } else {
            setUndecorated(true);
            GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].setFullScreenWindow(this);
        }
    }

    private void setCustomFontSizes() throws ParseException {
        String sizes = context.props().getCustomFontSizes();
        if (sizes != null) {
            String[] fontsizes = sizes.split(",");
            MessageFormat format = new MessageFormat("{0}({1})");
            for (String source : fontsizes) {
                Object[] parsedResult = format.parse(source);
                FONTS.valueOf((String) parsedResult[0]).alterDefaultSize(Integer.parseInt((String) parsedResult[1]));
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

        leftPanel.add(sideWings.getLeft());
        rightPanel.add(sideWings.getRight());
        bottomPanel.add(sideWings.getMenu());

        root.add(leftPanel, BorderLayout.WEST);
        root.add(rightPanel, BorderLayout.EAST);
        root.add(topPanel, BorderLayout.NORTH);
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

    /**
     * Switch scene. If current scene was the AlarmTime, then save the
     * configured alarm time (as we don't want to save to file on every click).
     * 
     * @param scene
     *            - the scene to change to.
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
    public void toggleMenuPanel() {
        sideWings.toggleMenu();
    }

}
