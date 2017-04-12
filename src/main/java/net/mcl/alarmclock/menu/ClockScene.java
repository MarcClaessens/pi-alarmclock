package net.mcl.alarmclock.menu;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.CSS;
import net.mcl.alarmclock.button.ButtonType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Scene for showing the clock.
 * Which buttons to show left or right, is defined in the properties file.
 */
/*
 * Layout
 * 
 *   +------------------------------+
 *   |      |                 |     |
 *   |      |                 |     |
 *   |  B1  |                 |  B3 |
 *   |      |  current time   |     |
 *   |      |                 |     |
 *   |  B2  |                 |  B4 |
 *   |      |                 |     |
 *   |      |                 |     |
 *   |------------------------------|
 *   |       weather report         |   
 *   +------------------------------+
 */

public class ClockScene extends Scene {
    private static final Logger LOGGER = LogManager.getLogger(ClockScene.class);

    private final BorderPane borderpane;

    private final List<Button> buttons = new ArrayList<>();
    private final Label messages;
    private final ClockTimeLabel clock;
    private final AppContext context;

    public ClockScene(AppContext context) {
        super(new BorderPane());
        this.borderpane = (BorderPane) getRoot();
        this.context = context;
        clock = new ClockTimeLabel(context);
        messages = new ClockMessageLabel(context);

        prepareButtons();
        borderpane.setLeft(getLeftButtonsNode());
        borderpane.setRight(getRightButtonsNode());
        borderpane.setBottom(getLabelNode(messages));
        borderpane.setCenter(getClock());
    }

    /**
     * Get the pane with the buttons that need to be shown on the left side.
     * The number of buttons to show on the left side is defined as a property.
     */
    private Node getLeftButtonsNode() {
        return getButtonsNode(buttons.subList(0, context.props().getButtonLeftCount()));
    }

    /**
     * Get the pane with the buttons that need to be shown on the right side.
     * The number of buttons to show is derived from the number shown on the left.
     */
    private Node getRightButtonsNode() {
        return getButtonsNode(buttons.subList(context.props().getButtonLeftCount(), buttons.size()));
    }

    private void prepareButtons() {
        int buttonscount = context.props().getButtonCount();
        for (int i = 1; i < buttonscount + 1; i++) {
            Button b = ButtonType.getClockButton(context, i);
            if (b != null) {
                buttons.add(b);
            } else {
                LOGGER.warn("Couldn't add clock scene button " + i);
            }
        }
    }

    private Node getClock() {
        CSS.CLOCK_FONT.applyStyle(clock);
        return getLabelNode(clock);
    }

    private Node getLabelNode(Label label) {
        TilePane tile = new TilePane();
        tile.getChildren().add(label);
        tile.setAlignment(Pos.CENTER);
        return tile;
    }

    private Node getButtonsNode(List<Button> buttons) {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        for (Button b : buttons) {
            vbox.getChildren().add(b);
        }
        return vbox;
    }

}
