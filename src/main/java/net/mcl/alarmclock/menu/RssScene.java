package net.mcl.alarmclock.menu;

import java.util.List;
import java.util.stream.Collectors;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import net.mcl.alarmclock.AppContext;
import net.mcl.alarmclock.CSS;
import net.mcl.alarmclock.button.ButtonType;
import net.mcl.alarmclock.feature.RssFeedListener;
import net.mcl.alarmclock.feature.RssSource;

public class RssScene extends Scene implements RssFeedListener {
	private final BorderPane borderpane;
	private final AppContext context;

	private final Label marquee = new Label();
	private String marqueeContent = null;


	public RssScene(AppContext context) {
		super(new BorderPane());
		this.borderpane = (BorderPane) getRoot();
		this.context = context;
		context.rss().registerListener(this);

		borderpane.setTop(getTop());
		borderpane.setCenter(getCenter());
		borderpane.setBottom(getBottom());
	}

	
	private void shiftRssPart(ActionEvent event) {
		if (marqueeContent != null) {
			if (marquee.getLayoutX() < marquee.getWidth() * -1) {
				marquee.setLayoutX(0);
			} else {
				marquee.setLayoutX(marquee.getLayoutX() - 1);
			}
			CSS.STANDARD_FONT.applyStyle(marquee);
		}

	}

	private Node getCenter() {
		Pane box = new Pane();
		
		box.setMaxWidth(context.screen().getWidth() * 0.99d);
		box.getChildren().add(marquee);
		borderpane.setCenter(box);
		CSS.STANDARD_FONT.applyStyle(box);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20L), this::shiftRssPart));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        return box;
	}
	
	private Node getTop() {
		HBox box = new HBox();
		box.setAlignment(Pos.CENTER);
		for (RssSource source: context.props().getRssSources()) {
			box.getChildren().add(new RssChoiceButton(context, source.getLabel(), source.getSourceUrl())); 
		}
		return box;
	}

	private Node getBottom() {
		HBox box = new HBox();
		box.setAlignment(Pos.CENTER);
		box.getChildren().add(ButtonType.CLOCKMENU.getButton(context));
		box.getChildren().add(ButtonType.BUTTONMENU.getButton(context));
		return box;
	}

	@Override
	public void rssLoading(String label) {
		marqueeContent = null;
		marquee.setLayoutX(0);
	}

	@Override
	public void rssContentChanged(List<String> content) {
		marqueeContent = content.stream().collect(Collectors.joining(" / "));
		marquee.setText(marqueeContent);
	}
}
