import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;


/*
Todo:
parser for BTBBTBT
input from files (separate class with static functions)
make sure tick offset and count offset is correct
 */


public class BSViewer extends Application {
    final int p = 2;
    final int q = 4;

    int[][] bsArrays = new int[4][1000];

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        for (int[] array: bsArrays) {
            Arrays.fill(array, 9);
        }

        BSPane bsPane = new BSPane( false);
        BorderPane borderPane = new BorderPane();
        HBox topMenu = new HBox();


        borderPane.setTop(topMenu);
        borderPane.setCenter(bsPane);
        Scene scene = new Scene(borderPane, 700, 500);

        // user interface

        Text bsInfo = new Text("p: " + String.valueOf(p) + "\nq: " + String.valueOf(q));
        CheckBox indicesBox = new CheckBox("Show Indices");
        TextField index = new TextField("Jump to index x on line q");

        topMenu.setSpacing(30);
        topMenu.setAlignment(Pos.CENTER);
        topMenu.getChildren().addAll(bsInfo, indicesBox);

        // handlers and listeners

        indicesBox.setOnAction(event -> {
            if (indicesBox.isSelected()) bsPane.setVisibleIndex(true);
            else bsPane.setVisibleIndex(false);
        });

        // zoom in by changing the tick spacing with scroll wheel
        scene.setOnScroll(event -> {
            if (event.getDeltaY() > 0) bsPane.incSpacing();
            else if (event.getDeltaY() < 0) bsPane.decSpacing();
        });

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case RIGHT:
                    bsPane.incIndex();
                    break;
                case LEFT:
                    bsPane.decIndex();
                    break;
                case R:
                    bsPane.resetSpacing();
                    break;
            }
        });

        scene.widthProperty().addListener(e -> {
            bsPane.drawBS();
        });

        primaryStage.setScene(scene);
        primaryStage.show();
        bsPane.drawBS();
    }

    class BSPane extends Pane {
        int height = 100; // pixels between horizontal lines
        double firstTickSpacing = 50; // since q > p, this is the minimal for tick spacing
        double tickSpacingScaling; // q/p when going up and p/q going down
        double firstYLocation;
        double tickSize = 5; // height above line in pixels
        double drawStart = 0;
        boolean direction = false;
        double lineSpacing = 100; // space between lines (maybe should be negative for when going up
        int mod;
        boolean showVerticalLine = false;
        // counts used for knowing when to access arrays.

        boolean visibleIndex = false; // display absolute count with relative count

        public BSPane(boolean direction) {
            this.direction = direction;

            if (direction) {
                lineSpacing = -100;
                mod = p; // if up we go over every q to draw lines
                tickSpacingScaling = (double) q / p;
            }
            else {
                mod = q;
                tickSpacingScaling = (double) p / q;
            }

            drawBS();
        }

        // An idea: full redraw and just a number redraw depending on user actions

        // everything is relative to the index of the first array
        // make a more general BS main line object? Then we can add more than two and it will be easier (since its more general)
        // The bs line connects into the BSPane object where:
        // BSPane controls the spacing of the ticks on bs line,
        //      the starting index

        // bs line has the properties: array, bool visibleIndex, tickSpacing, tick offset, ticks, a line, and horizontal line (up/down boolean)
        // and a reference to the previous bs line to know what the tick spacing/offset should be
        // bs line has static y value to know spacing between main lines

        public void drawBS() {
            getChildren().clear();

            setTranslateX(30); // change based on max label size like BBT

            if (direction) {
                // if up we start drawing the line towards the bottom
                firstYLocation = getHeight() - 100;
            }
            else {
                firstYLocation = 100;
            }

            double lineY = firstYLocation;
            double tickOffset = 0;
            double tickSpacing = firstTickSpacing;

            showVerticalLine = false;

            for (int[] coordinates: bsArrays) {
                drawLines(lineY, tickOffset, tickSpacing, coordinates, 0);

                showVerticalLine = true; // true after the first line is drawn
                lineY += lineSpacing;
                //tickOffset *=
                tickSpacing *= tickSpacingScaling;
            }
        }

        private void drawLines(double lineY, double tickOffset, double tickSpacing, int[] coordinates, int arrayIndex) {
            // arrayIndex is where to start in the array, and arrayNumber is which array, might need to be int[]
            int count = 0;
            Line line = new Line(0, lineY, widthProperty().doubleValue(), lineY);
            getChildren().add(line);

            for (double i = 0; i < widthProperty().doubleValue(); i += tickSpacing) {
                Line tick = new Line(i, lineY - tickSize, i, lineY + tickSize);
                Text number = new Text(String.valueOf(coordinates[count]));
                number.setX(i - number.getLayoutBounds().getWidth() / 2);
                number.setY(lineY - 10);
                getChildren().addAll(tick, number);

                // throw everything above in this if statement and offset the numbers to the left or right for visibility?
                if ((int) (i / tickSpacing) % mod == 0 && showVerticalLine) { // connecting vertical lines
                    Line edge = new Line(i, lineY, i, lineY - lineSpacing); // line goes the opposite way the lines are being built
                    edge.setOpacity(.6);
                    getChildren().add(edge);
                }

                if (visibleIndex) {
                    Text absCount = new Text(String.valueOf(count));
                    absCount.setX(i - absCount.getLayoutBounds().getWidth() / 2);
                    absCount.setY(lineY + 20); // put index under the tick marks
                    getChildren().add(absCount);
                }
                count++;
            }
        }

        // ** Visual change control methods **

        public void incSpacing() {
            firstTickSpacing += 5;
            drawBS();
        }

        public void decSpacing() {
            if (firstTickSpacing - 5 > 5) {
                firstTickSpacing -= 5;
                drawBS();
            }
        }

        public void resetSpacing() {
            firstTickSpacing = 50;
            drawBS();
        }

        public void setVisibleIndex(boolean visibleIndex) {
            this.visibleIndex = visibleIndex;
            drawBS();
        }

        public void incIndex() {
            drawStart += q * firstTickSpacing;
            drawBS();
        }

        public void incIndex(int x) {
            drawStart += x * q * firstTickSpacing;
            drawBS();
        }

        public void decIndex() {
            if (drawStart - q * firstTickSpacing >= 0) {
                drawStart -= q * firstTickSpacing;
                drawBS();
            }
        }

        public void resetStart() {
            drawStart = 0;
            drawBS();
        }
    }
}

