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

public class BSViewer extends Application {
    final int p = 5;
    final int q = 7;

    int[][] bsArrays = new int[3][1000];

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        for (int[] array: bsArrays) {
            for (int i = 0; i < array.length; i++) {
                array[i] = (int) Math.random() * 5;
            }
        }

        BSPane bsPane = new BSPane(0, false);
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
                    bsPane.incStart();
                    break;
                case LEFT:
                    bsPane.decStart();
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
        double topLineY = 200;
        double botLineY = topLineY + height;
        double tickSize = 5; // height above line in pixels
        double drawStart = 0;
        // counts used for knowing when to access arrays.
        int topCount = 0;
        int botCount = 0;

        boolean visibleIndex = false; // display absolute count with relative count


        // use enum instead of boolean for direction?
        public BSPane(int offset, boolean direction) {
            // draw horizontal lines to the end of the pane
            // direction affects which line will have the offset applied
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

            for (int[] coordinates: bsArrays) {
                drawLines();
            }

            for ()

        }

        private void drawLines(double tickOffset, double tickSpacing, int[] coordinates, int arrayIndex) {
            // arrayIndex is where to start in the array, and arrayNumber is which array, might need to be int[]
            double midHeight = heightProperty().doubleValue() / 2;
            int count = 0;
            Line line = new Line(0, midHeight, widthProperty().doubleValue(), midHeight);
            getChildren().add(line);

            for (double i = 0; i < widthProperty().doubleValue(); i += tickSpacing) {
                Line tick = new Line(i, midHeight - tickSize, i, midHeight + tickSize);
                Text number = new Text(String.valueOf(coordinates[count]));
                number.setX(i - number.getLayoutBounds().getWidth() / 2);
                number.setY(midHeight - 10);
                getChildren().addAll(tick, number);
                if ((i / tickSpacing) % mod == 0) {
                    Line edge = new Line(i, midHeight, i, midHeight + 50); // midHeight + vert line offset
                    edge.setOpacity(.7);
                    getChildren().add(edge); // add to a separate group/pane that is resized by the BSPane?
                }
                if (visibleIndex) {
                    Text index = new Text(String.valueOf(count));
                    index.setX(i - index.getLayoutBounds().getWidth() / 2);
                    index.setY(midHeight + 20); // put index under the tick marks
                    getChildren().add(index);
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

        public void incStart() {
            drawStart += q * firstTickSpacing;
            drawBS();
        }

        public void incStart(int x) {
            drawStart += x * q * firstTickSpacing;
            drawBS();
        }

        public void decStart() {
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

