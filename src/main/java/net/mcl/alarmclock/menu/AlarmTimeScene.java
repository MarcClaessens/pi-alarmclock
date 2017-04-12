package net.mcl.alarmclock.menu;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.CSS;
import net.mcl.alarmclock.button.ButtonType;

/**
 * Scene to set the alarm time.  
 * 
 * Time can be changed by clicking buttons.
 * There are 2 buttons to add / subtract 1 hour per click.
 * There are 2 buttons to add / subtract 10 minutes per click.
 * There are 2 buttons to add / subtract 1 minute per click.
 * 
 * Changing the minutes can update the hour: 08:00 - 10 minutes becomes 07:50
 * 
 * The time is saved when this scene is exited (see Main).
 * Note that the up/down buttons are likely to become misaligned when changing the font.
 */
public class AlarmTimeScene extends Scene {
    private final BorderPane borderpane;
    private final AppContext context;
    private final ClockTimeLabel clock;

    public AlarmTimeScene(AppContext context) {
        super(new BorderPane());
        this.context = context;
        this.borderpane = (BorderPane) getRoot();
        this.clock = new ClockTimeLabel(context, false);

        borderpane.setCenter(getClock());
        borderpane.setBottom(getBottom());
    }

    private Node getBottom() {
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(ButtonType.CLOCKMENU.getButton(context));
        box.getChildren().add(ButtonType.BUTTONMENU.getButton(context));
        return box;
    }

    private Node getClock() {
        final GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));
        grid.setAlignment(Pos.CENTER);

        //grid.setGridLinesVisible(true);

        final TimeAdjustButton hoursup = new TimeAdjustButton(context, true, true, 1, true);
        final TimeAdjustButton mins10up = new TimeAdjustButton(context, true, false, 10);
        final TimeAdjustButton mins1up = new TimeAdjustButton(context, true, false, 1);
        final TimeAdjustButton hoursdown = new TimeAdjustButton(context, false, true, 1, true);
        final TimeAdjustButton mins10down = new TimeAdjustButton(context, false, false, 10);
        final TimeAdjustButton mins1down = new TimeAdjustButton(context, false, false, 1);

        grid.add(hoursup, 0, 0);
        grid.add(mins10up, 1, 0);
        grid.add(mins1up, 2, 0);

        CSS.CLOCK_FONT.applyStyle(clock);
        grid.add(clock, 0, 1, 3, 1);

        grid.add(hoursdown, 0, 2);
        grid.add(mins10down, 1, 2);
        grid.add(mins1down, 2, 2);

        GridPane.setHalignment(clock, HPos.CENTER);

        return grid;
    }

}
