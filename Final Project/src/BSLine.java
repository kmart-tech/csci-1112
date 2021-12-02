import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Observable;

// add each BSLine pane to the big boy

//extend customnode?
public class BSLine extends Pane {
    int[] coordinates;
    double tickOffset = 0;
    double tickSpacing = 50;
    double tickSize = 5;
    boolean visibleIndex = false;
    int mod;
    //private int count = 0; // dont need in initialization
    //ArrayList<Node> bsElems = new ArrayList<Node>();

    public BSLine(int[] coordinates,double tickSpacing, double tickOffset, int mod, boolean visibleIndex){
        // should be tick spacing, start array index, and p/q then we calculate tickOffset
        this.coordinates = coordinates;
        this.tickSpacing = tickSpacing;
        this.tickOffset = tickOffset;
        this.visibleIndex = visibleIndex;
        this.mod = mod; // p or q
        drawLine();
    }

    public void drawLine() {
        //input for offset stuff?
        getChildren().clear();

        double midHeight = heightProperty().doubleValue() / 2;
        int count = 0;

        // draw horizontal main line
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

    public void drawVertical() {
        // draw vertical lines (separate?)
    }

    public ObservableList<Node> returnChildren() {
        return getChildren();
    }

    public void setWidthManual(double value) {
        setWidth(value);
    }
}

