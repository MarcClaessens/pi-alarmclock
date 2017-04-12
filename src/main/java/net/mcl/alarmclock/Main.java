package net.mcl.alarmclock;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import net.mcl.alarmclock.feature.MainContext;
import net.mcl.alarmclock.menu.AlarmTimeScene;
import net.mcl.alarmclock.menu.ClockScene;
import net.mcl.alarmclock.menu.MenuScene;
import net.mcl.alarmclock.menu.RssScene;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main application.
 * 
 *
 */
public class Main extends Application implements Screen {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    private static final double WIDTH = 5.74 * 96;
    private static final double HEIGHT = 4.00 * 96;

    private Stage stage = null;
    private Scene clockscene;
    private Scene menuscene;
    private Scene rssscene;
    private Scene alarmtimescene;
    private Scene currentscene;
    private AppContext context;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    /**
     * Set application CSS, load Web Fonts and create AppContext instance.
     */
    @Override
    public void init() throws Exception {
        super.init();

        String appcss = getClass().getResource("/css/application.css").toExternalForm();
        setUserAgentStylesheet(appcss);

        loadFonts("/fonts/digital-7-(mono).ttf", "/fonts/fontawesome-webfont.ttf",
                "/fonts/materialdesignicons-webfont.ttf");

        context = new MainContext(this);
    }

    private void loadFonts(String... resources) {
        for (String resource : resources) {
            Font font = Font.loadFont(Main.class.getResource(resource).toExternalForm(), 18);
            LOGGER.debug("Loaded font : " + font);
        }
    }

    /**
     * Center the stage and create scenes.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Alarm Clock");
        primaryStage.setFullScreenExitHint("");
        stage = primaryStage;

        context.screen().exitFullScreen();

        createScenes();
        setClockScene();
    }

    /**
     * Create scenes and add stylesheets.
     */
    private void createScenes() {
        clockscene = new ClockScene(context);
        menuscene = new MenuScene(context);
        alarmtimescene = new AlarmTimeScene(context);
        rssscene = new RssScene(context);
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
    private void setScene(Scene scene) {
        if (currentscene == alarmtimescene) {
            context.alarmClock().saveAlarmTime();
        }
        currentscene = scene;
        stage.hide();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Get screen height.
     */
    @Override
    public double getHeight() {
        return stage.getHeight();
    }

    /**
     * Get screen width.
     */
    @Override
    public double getWidth() {
        return stage.getWidth();
    }

    /**
     * Set to full screen.
     */
    @Override
    public void fullScreen() {
        stage.setFullScreen(true);
    }

    /**
     * Set screen to default size and center it.
     */
    @Override
    public void exitFullScreen() {
        stage.setFullScreen(false);
        stage.setHeight(HEIGHT);
        stage.setWidth(WIDTH);
        stage.centerOnScreen();
    }

    /**
     * Returns true if the application is using full screen.
     */
    @Override
    public boolean isFullScreen() {
        return stage.isFullScreen();
    }

}
