package net.mcl.alarmclock;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.text.MessageFormat;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import net.mcl.alarmclock.feature.MainContext;
import net.mcl.alarmclock.menu.AlarmTimeScene;
import net.mcl.alarmclock.menu.ClockScene;
import net.mcl.alarmclock.menu.MenuScene;
import net.mcl.alarmclock.menu.RssScene;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main application.
 */
public class Main extends JFrame implements AppScreen {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    private JPanel clockscene;
    private JPanel menuscene;
    private JPanel rssscene;
    private JPanel alarmtimescene;
    private JPanel currentscene;
    private AppContext context;

    public static void main(String[] args) throws Exception {
        try {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // default should be cross-platform look-and-feel
                e.printStackTrace();
            }
            Main app = new Main();
            app.setSize(WIDTH, HEIGHT);

            app.init();

        } catch (Exception e) {
            LOGGER.error(e);
            throw e;
        }
    }

    /**
     * Set application CSS, load Web Fonts and create AppContext instance.
     */
    public void init() throws Exception {
        try {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            loadFonts("/fonts/digital-7-(mono).ttf", "/fonts/fontawesome-webfont.ttf",
                    "/fonts/materialdesignicons-webfont.ttf");

            context = new MainContext(this);

            setCustomFontSizes();
            getContentPane().setBackground(Color.black);
            setFullScreen();

            createScenes();
            setVisible(true);

            getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        } catch (Exception e) {
            LOGGER.error(e);
            throw e;
        }
    }

    private void setFullScreen() {
        setUndecorated(true);
        GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].setFullScreenWindow(this);
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

    /**
     * Create scenes and add style sheets.
     */
    private void createScenes() {
        alarmtimescene = new AlarmTimeScene(context);
        menuscene = new MenuScene(context);
        clockscene = new ClockScene(context);
        rssscene = new RssScene(context);

        setAlarmTimeScene();
        setMenuScene();
        setRssScene();
        setClockScene();
    }

    /**
     * Change main scene to Clock.
     */
    @Override
    public void setClockScene() {
        setScene(clockscene);
    }

    /**
     * Change main scene to Menu.
     */
    @Override
    public void setMenuScene() {
        setScene(menuscene);
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
            currentscene.setVisible(false);
            getContentPane().remove(currentscene);
        }
        getContentPane().add(scene);
        pack();
        scene.setVisible(true);
        currentscene = scene;
    }
}
