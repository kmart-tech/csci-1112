/*
Kevin Martinsen
CSCI 1112 - Object Oriented II
Date: 11/10/2021

Exercise 14-28: Clock Pane implementation
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.Clock;

public class TestClock extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ClockPane clock = new ClockPane((int)(Math.random() * 11), (int)(Math.random() * 30), 0);
        clock.setSecondHandVisible(false);

        Scene scene = new Scene(clock, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
