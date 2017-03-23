package net.mcl.alarmclock.menu;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.button.ButtonType;

public class MenuScene extends Scene {
    private final FlowPane flowPane;
    private final AppContext context;

    public MenuScene(AppContext context) {
        super(new FlowPane());
        this.context = context;
        flowPane = (FlowPane) getRoot();
        prepareButtons();
    }

    private void prepareButtons() {
        flowPane.setVgap(10);
        flowPane.setHgap(10);
        flowPane.setAlignment(Pos.CENTER);
        //System.out.println("Menu Scene buttons :");
        for (ButtonType t : ButtonType.values()) {
            if (t.isAllowedInButtonMenu()) {
                Button b = t.getButton(context);
                flowPane.getChildren().add(b);
                //System.out.println(b);
            }
        }
    }
}
