/*
Kevin Martinsen
CSCI 1112 - OOP 2
11/17/2021

Exercise 16-21: Countdown timer with song
 */

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Exercise21 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane pane = new StackPane();

        TextField text = new TextField();
        text.setPromptText("Enter Number");
        text.setAlignment(Pos.CENTER);
        text.setStyle("-fx-border-color: null; -fx-font-size: 50; -fx-focus-color: null; -fx-background-color: null;");
        pane.getChildren().add(text);

        //int countdown = 10;

        Media song = new Media("https://www.free-stock-music.com/music/scandinavianz-timeout.mp3");
        MediaPlayer playSong = new MediaPlayer(song);


        // countdown handler, decrements the text field by one
        EventHandler<ActionEvent> countdownHandler = e -> {
            int count = Integer.parseInt(text.getText());
            if (count - 1 > 0) {
                text.setText("" + (count - 1));
            }
            else {
                text.setText("0");
                playSong.play();
            }
        };

        // setup timeline animation to update every second
        Timeline animation = new Timeline(new KeyFrame(Duration.seconds(1), countdownHandler));
        animation.setCycleCount(Timeline.INDEFINITE);

        // Text field handler and check for valid integer
        text.setOnAction(e -> {
            try {
                Integer.parseInt(text.getText());
                animation.play();
                pane.requestFocus();
            }
            catch (NumberFormatException ex) {
                text.setText("");
                text.setPromptText("Invalid Number");
                text.getParent().requestFocus();
                animation.pause();
            }
        });

        Scene scene = new Scene(pane, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Exercise16_21");
        text.getParent().requestFocus(); // change focus so we see the prompted text "Enter Number"
        primaryStage.show();
    }
}
