/*
Kevin Martinsen
CSCI 1112 - OOP 2
11/16/2021

Exercise 16_1 - Change color (outline) and location of text
 */
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Exercise1 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Set up panes and positioning
        BorderPane mainPane = new BorderPane();
        Pane textPane = new Pane(); // pane or stack pane?
        HBox radioPane = new HBox();
        HBox buttonPane = new HBox();

        mainPane.setTop(radioPane);
        mainPane.setCenter(textPane);
        mainPane.setBottom(buttonPane);

        radioPane.setAlignment(Pos.CENTER);
        radioPane.setSpacing(10);
        buttonPane.setAlignment(Pos.CENTER);

        Scene scene = new Scene(mainPane, 500, 300);


        // setup text in center pane
        Text centerText = new Text( 100, 100, "Programming is fun!");
        textPane.getChildren().add(centerText);
        textPane.setStyle("-fx-border-color: black; -fx-font-size: 20pt;");

        // set up radio buttons and toggle group
        RadioButton rbRed = new RadioButton("Red");
        RadioButton rbYellow = new RadioButton("Yellow");
        RadioButton rbBlack = new RadioButton("Black");
        RadioButton rbOrange = new RadioButton("Orange");
        RadioButton rbGreen = new RadioButton("Green");

        radioPane.getChildren().addAll(rbRed, rbYellow, rbBlack, rbOrange, rbGreen);

        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(rbRed, rbYellow, rbBlack, rbOrange, rbGreen);

        // setup radio button logic
        rbRed.setOnAction(e -> {
            if (rbRed.isSelected()) centerText.setStroke(Color.RED);
        });

        rbYellow.setOnAction(e -> {
            if (rbYellow.isSelected()) centerText.setStroke(Color.YELLOW);
        });

        rbBlack.setOnAction(e -> {
            if (rbBlack.isSelected()) centerText.setStroke(Color.BLACK);
        });

        rbOrange.setOnAction(e -> {
            if (rbOrange.isSelected()) centerText.setStroke(Color.ORANGE);
        });

        rbGreen.setOnAction(e -> {
            if (rbGreen.isSelected()) centerText.setStroke(Color.GREEN);
        });

        // Setup movement buttons and logic
        Button btLeft = new Button("<=");
        Button btRight = new Button("=>");
        buttonPane.getChildren().addAll(btLeft, btRight);

        btLeft.setOnAction(e -> {
            if (centerText.getX() - 10 > 0) centerText.setX(centerText.getX() - 10);
        });

        btRight.setOnAction(e -> {
            if (centerText.getX() + centerText.getLayoutBounds().getWidth() + 10 < scene.getWidth()) centerText.setX(centerText.getX() + 10);
        });

        // setup and display the scene
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
