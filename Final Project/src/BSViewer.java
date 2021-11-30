import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import javax.swing.*;

public class BSViewer extends Application {
    final int p = 3;
    final int q = 5;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        BSPane pane = new BSPane(0, false);


        Scene scene = new Scene(pane, 500,500);

        scene.widthProperty().addListener(e -> {
            pane.drawBS();
        });

        pane.drawBS();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // maybe ticks are in a group object that has spacing as a value to auto draw

    class BSPane extends Pane {
        int height = 100; // pixels between horizontal lines
        int minTickSpacing = 50; // minimal spacing between ticks
        int topLineY = 200;
        int botLineY = topLineY + height;
        int tickSize = 5; // height above line in pixels


        // as an inner class we can see p and q
        // use enum instead of boolean for direction?
        public BSPane(int offset, boolean direction) {
            // draw horizontal lines to the end of the pane
            // direction affects which line will have the offset applied
            drawBS();
        }

        public void drawBS() {
            Line topLine = new Line(0, topLineY,100, topLineY);
            Line botLine = new Line(0, botLineY,100, botLineY);

            getChildren().clear();
            getChildren().addAll(topLine, botLine);

            // draw tick marks on the bottom line every q
            // maybe needs to be doubleValue()?
            for (int i = 0; i < widthProperty().intValue(); i += minTickSpacing) {
                Line tick = new Line(i, botLineY - tickSize, i, botLineY + tickSize);
                getChildren().add(tick);
                if ((i / minTickSpacing) % q == 0) {
                    Line edge = new Line(i, botLineY, i , topLineY);
                    getChildren().add(edge);
                }
            }


            int pTickSpacing = (minTickSpacing * q) / p;

            // draw tick marks on the top line every p

            for (int i = 0; i < widthProperty().intValue(); i += pTickSpacing) {
                Line tick = new Line(i, topLineY - tickSize, i, topLineY + tickSize);
                getChildren().add(tick);
            }


            topLine.endXProperty().bind(widthProperty());
            botLine.endXProperty().bind(widthProperty());
        }

        public void setMinTickSpacing(int minTickSpacing) {
            this.minTickSpacing = minTickSpacing;
        }
    }
}

