/*
Kevin Martinsen
CSCI 1112 - OOP 2
11/15/2021

Chapter 15
Exercise 15 - Move a ball with buttons and arrow keys
 */

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class Exercise_Listeners extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // create the panes
        BorderPane mainPane = new BorderPane();
        HBox buttonsPane = new HBox(10);
        CirclePane circlePane = new CirclePane();
        buttonsPane.setSpacing(10);
        buttonsPane.setAlignment(Pos.CENTER);

        mainPane.setCenter(circlePane);
        mainPane.setBottom(buttonsPane);

        // create scene and place it in the stage
        Scene scene = new Scene(mainPane, 500, 300);
        primaryStage.setTitle("Control Ball - Exercise 15-15");
        primaryStage.setScene(scene);

        // create buttons and add to buttonsPane
        Button btLeft = new Button("Left");
        Button btRight = new Button("Right");
        Button btUp = new Button("Up");
        Button btDown = new Button("Down");
        buttonsPane.getChildren().addAll(btLeft, btRight, btUp, btDown);

        // button handlers
        btLeft.setOnAction(e -> {
            circlePane.moveCircle(-5,0);
        });

        btRight.setOnAction(e -> {
            circlePane.moveCircle(5,0);
        });

        btUp.setOnAction(e -> {
            circlePane.moveCircle(0,-5);
        });

        btDown.setOnAction(e -> {
            circlePane.moveCircle(0,5);
        });


        // arrow keys handlers
        scene.setOnKeyPressed(e -> {
            switch(e.getCode()) {
                case LEFT: circlePane.moveCircle(-5,0); break;
                case RIGHT: circlePane.moveCircle(5,0); break;
                case UP: circlePane.moveCircle(0,-5); break;
                case DOWN: circlePane.moveCircle(0,5); break;
            }
        });

        // show the stage
        primaryStage.show();
    }
}

class CirclePane extends Pane {
    private double radius = 20;
    private double x = 50;
    private double y = 50;
    private Circle circle = new Circle(x,y,radius);


    public CirclePane() {
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        getChildren().add(circle);
    }

    public void moveCircle(double dx, double dy) {
        // boundries check
        if (x + dx - radius < 0 || x + dx + radius > getWidth()
                || y + dy - radius < 0 || y + dy + radius > getHeight()) {
            return;
        }

        // move the ball
        x += dx;
        y += dy;
        circle.setCenterX(x);
        circle.setCenterY(y);
    }
}
