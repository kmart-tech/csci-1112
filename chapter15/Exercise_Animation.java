/*
Kevin Martinsen
CSCI 1112 - OOP 2
11/15/2021

Chapter 15
Animation - move a rectangle on the path of a pentagon
 */

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;



public class Exercise_Animation extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane pane = new StackPane();
        Group group = new Group();

        Rectangle rectangle = new Rectangle(50, 50);
        rectangle.setFill(Color.RED);

        // create the pentagon
        Polygon pentagon = new Polygon();
        pentagon.setFill(Color.BLACK);
        pentagon.setStroke(Color.BLACK); // change to null
        ObservableList<Double> list = pentagon.getPoints();
        double radius = 100;
        double centerX = pane.getWidth() / 2;
        double centerY = pane.getHeight() / 2;

        // Add points to the pentagon list
        for (int i = 0; i < 5; i++) {
            list.add(centerX + radius * Math.cos(2 * i * Math.PI / 5));
            list.add(centerY - radius * Math.sin(2 * i * Math.PI / 5));
        }
        //pentagon.setRotate(-18);

        group.getChildren().addAll(pentagon, rectangle);

        pane.getChildren().add(group);

        // create path transition animation
        PathTransition pt = new PathTransition();
        pt.setDuration(Duration.seconds(5));
        pt.setPath(pentagon);
        pt.setNode(rectangle);
        //pt.setOrientation
        pt.setCycleCount(Timeline.INDEFINITE);
        pt.setAutoReverse(false);
        pt.play();

        Scene scene = new Scene(pane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}


