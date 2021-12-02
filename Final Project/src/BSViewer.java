import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class BSViewer extends Application {
    final int p = 5;
    final int q = 7;

    int[] topArray = new int[10000];
    int[] botArray = new int[10000];

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Arrays.fill(topArray, 8);
        Arrays.fill(botArray, 11);
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
            if (indicesBox.isSelected()) bsPane.setShowIndices(true);
            else bsPane.setShowIndices(false);
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
        double qTickSpacing = 50; // since q > p, this is the minimal for tick spacing
        double topLineY = 200;
        double botLineY = topLineY + height;
        double tickSize = 5; // height above line in pixels
        double drawStart = 0;
        // counts used for knowing when to access arrays.
        int topCount = 0;
        int botCount = 0;

        boolean showIndices = false; // display absolute count with relative count


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

            double pTickSpacing = (qTickSpacing * q) / (double) p;

            // calculate where to start for topArray and botArray
            topCount = (int) (drawStart / pTickSpacing);
            botCount = (int) (drawStart / qTickSpacing);

            BSLine mainline = new BSLine(topArray,qTickSpacing,0,q,true);

            mainline.setLayoutY(200);
            mainline.setLayoutX(10);

            //mainline.setWidthManual(getWidth());


            mainline.drawLine();

            getChildren().add(mainline);


            System.out.println(mainline.getWidth());
        }

        public void incSpacing() {
            qTickSpacing += 5;
            drawBS();
        }

        public void decSpacing() {
            if (qTickSpacing - 5 > 5) {
                qTickSpacing -= 5;
                drawBS();
            }
        }

        public void resetSpacing() {
            qTickSpacing = 50;
            drawBS();
        }

        public void setShowIndices(boolean showIndices) {
            this.showIndices = showIndices;
            drawBS();
        }

        public void incStart() {
            drawStart += q * qTickSpacing;
            drawBS();
        }

        public void incStart(int x) {
            drawStart += x * q * qTickSpacing;
            drawBS();
        }

        public void decStart() {
            if (drawStart - q * qTickSpacing >= 0) {
                drawStart -= q * qTickSpacing;
                drawBS();
            }
        }

        public void resetStart() {
            drawStart = 0;
            drawBS();
        }
    }
}

