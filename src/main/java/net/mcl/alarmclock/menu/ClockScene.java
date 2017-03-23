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

/*
 * Layout
 * 
 *   +------------------------------+
 *   |      |                 |     |
 *   |      |                 |     |
 *   |  B1  |                 |  B3 |
 *   |      |    Clock        |     |
 *   |      |                 |     |
 *   |  B2  |                 |  B4 |
 *   |      |                 |     |
 *   |      |                 |     |
 *   |------------------------------|
 *   |       any messages           |   
 *   +------------------------------+
 */

public class ClockScene extends Scene {
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

    private Node getLeftButtonsNode() {
        return getButtonsNode(buttons.subList(0, context.props().getButtonLeftCount()));
    }

    private Node getRightButtonsNode() {
        return getButtonsNode(buttons.subList(context.props().getButtonLeftCount(), buttons.size()));
    }

    private void prepareButtons() {
        int buttonscount = context.props().getButtonCount();
        //System.out.println("Clock Scene buttons : ");
        for (int i = 1; i < buttonscount + 1; i++) {
            Button b = ButtonType.getClockButton(context, i);
            if (b != null) {
                //System.out.println(b);
                buttons.add(b);
            } else {
                System.out.println("Couldn't add clock scene button " + i);
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
