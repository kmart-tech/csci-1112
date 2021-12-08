import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;


/*
Todo:
parser for BTBBTBT
input from files (separate class with static functions)
use B/b offsets in the drawBS method (should just be Bcount + offset count stuff)
 */


public class BSViewer extends Application {
    int p = 2;
    int q = 4;

    int[][] bsArrays = new int[4][1000];

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        for (int[] array: bsArrays) {
            Arrays.fill(array, 9);
        }

        // initial input for p, q, and array files
        VBox vbox = new VBox();


        HBox pqEntries = new HBox();
        pqEntries.setAlignment(Pos.CENTER);
        TextField pEntry = new TextField("p");
        TextField qEntry = new TextField("q");
        pqEntries.getChildren().addAll(pEntry,qEntry);
        vbox.getChildren().addAll(pqEntries);

        // set directory to look in then automatically open files based on BBBTBT? path?

        TextField file1 = new TextField();
        file1.setPromptText("File 1");

        TextField file2 = new TextField();
        file2.setPromptText("File 2");

        Button continueButton = new Button("Continue");

        vbox.getChildren().addAll(file1,file2, continueButton);

        Scene initialScene = new Scene(vbox, 700, 500);
        primaryStage.setScene(initialScene);
        primaryStage.show();

        continueButton.setOnAction(e -> {
            try {
                p = Integer.parseInt(pEntry.getText());
                q = Integer.parseInt(qEntry.getText());
                BSPane bsPane = new BSPane(false);
                Scene mainScene = new Scene(bsPane, 700, 500);
                primaryStage.setScene(mainScene);
            }
            catch (NumberFormatException ex) {
                //
            }
        });
    }

    class BSPane extends BorderPane {
        int height = 100; // pixels between horizontal lines
        double firstTickSpacing = 50; // since q > p, this is the minimal for tick spacing
        double tickSpacingScaling; // q/p when going up and p/q going down
        double firstYLocation;
        double tickSize = 5; // height above line in pixels
        int indexStart = 0;
        boolean direction = false;
        double lineSpacing = 100; // space between lines (maybe should be negative for when going up
        int currentMod;
        int nextMod; // the mod of the next line (replace with an if statement in the drawBS method?
        boolean showVerticalLine;
        boolean visibleIndex = false; // display absolute count with relative count

        Pane centerPane = new Pane();

        public BSPane(boolean direction) {
            this.direction = direction;

            if (direction) {
                lineSpacing = -100;
                currentMod = p; // if the sheet is going up we draw lines down every p
                nextMod = q;
                tickSpacingScaling = (double) q / p;
            }
            else {
                currentMod = q;
                nextMod = p;
                tickSpacingScaling = (double) p / q;
            }

            HBox topMenu = new HBox();

            setTop(topMenu);
            setCenter(centerPane);

            Text bsInfo = new Text("p: " + String.valueOf(p) + "\nq: " + String.valueOf(q));
            CheckBox indicesBox = new CheckBox("Show Indices");
            TextField indexField = new TextField("Jump to index x");

            topMenu.setSpacing(30);
            topMenu.setAlignment(Pos.CENTER);
            topMenu.getChildren().addAll(bsInfo, indicesBox, indexField);

            // handlers and listeners

            indicesBox.setOnAction(event -> {
                if (indicesBox.isSelected()) setVisibleIndex(true);
                else setVisibleIndex(false);
                requestFocus();
            });

            indexField.setOnAction(e -> {
                try {
                    setIndex(Integer.parseInt(indexField.getText()));
                    requestfocus();
                }
                catch (NumberFormatException ex) {
                    indexField.setText("");
                    indexField.setPromptText("Invalid Number");
                    requestFocus();
                }
            });

            // zoom in by changing the tick spacing with scroll wheel
            setOnScroll(event -> {
                if (event.getDeltaY() > 0) incSpacing();
                else if (event.getDeltaY() < 0) decSpacing();
            });

            setOnKeyPressed(event -> {
                //bsPane.requestFocus();
                switch (event.getCode()) {
                    case RIGHT:
                        incIndex();
                        break;
                    case LEFT:
                        decIndex();
                        break;
                    case R:
                        resetSpacing();
                        break;
                }
            });

            widthProperty().addListener(e -> {
                drawBS();
            });

            drawBS();
        }

        public void drawBS() {
            centerPane.getChildren().clear();

            setTranslateX(30); // change based on max label size like BBT

            if (direction) {
                // if up we start drawing the line towards the bottom
                firstYLocation = getHeight() - 100;
            }
            else {
                firstYLocation = 100;
            }

            double lineY = firstYLocation;
            double tickSpacing = firstTickSpacing;
            int arrayIndex = indexStart;
            //double tickOffsetX = (indexStart % p) * tickSpacing;
            double tickOffsetX = 0;

            showVerticalLine = false;

            for (int[] coordinates: bsArrays) {
                drawLines(lineY, tickOffsetX, tickSpacing, coordinates, arrayIndex);

                showVerticalLine = true; // true after the first line is drawn
                double currentTickOffset =  (arrayIndex % nextMod) * tickSpacing; // p going down
                arrayIndex = (arrayIndex / nextMod) * currentMod + (int) Math.ceil(((arrayIndex % nextMod) * currentMod) / (double) nextMod); // divide by p since going down
                lineY += lineSpacing;
                tickSpacing *= tickSpacingScaling;
                tickOffsetX = tickOffsetX + (arrayIndex % currentMod) * tickSpacing - currentTickOffset;
            }
        }

        private void drawLines(double lineY, double tickOffsetX, double tickSpacing, int[] coordinates, int arrayIndex) {
            Line line = new Line(0, lineY, widthProperty().doubleValue(), lineY);
            centerPane.getChildren().add(line);

            for (double i = 0; i < widthProperty().doubleValue(); i += tickSpacing) {
                Line tick = new Line(i + tickOffsetX, lineY - tickSize, i + tickOffsetX, lineY + tickSize);
                Text number = new Text(String.valueOf(coordinates[arrayIndex]));
                number.setX(i + tickOffsetX - number.getLayoutBounds().getWidth() / 2);
                number.setY(lineY - 10);
                centerPane.getChildren().addAll(tick, number);

                // throw everything above in this if statement and offset the numbers to the left or right for visibility?
                if (arrayIndex % currentMod == 0 && showVerticalLine) { // connecting vertical lines
                    Line edge = new Line(i + tickOffsetX, lineY, i + tickOffsetX, lineY - lineSpacing); // line goes the opposite way the lines are being built
                    edge.setOpacity(.6);
                    centerPane.getChildren().add(edge);
                }

                if (visibleIndex) {
                    Text absCount = new Text(String.valueOf(arrayIndex));
                    absCount.setX(i + tickOffsetX - absCount.getLayoutBounds().getWidth() / 2);
                    absCount.setY(lineY + 20); // put index under the tick marks
                    centerPane.getChildren().add(absCount);
                }
                arrayIndex++;
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
            indexStart += 1;
            drawBS();
        }

        public void decIndex() {
            if (indexStart - 1 >= 0) {
                indexStart -= 1;
                drawBS();
            }
        }

        public void setIndex(int x) {
            if (x >= 0 && x < bsArrays[0].length) {
                indexStart = x;
                drawBS();
            }
        }

        public void resetStart() {
            indexStart = 0;
            drawBS();
        }

        public void requestfocus() {
            centerPane.requestFocus();
        }
    }
}

