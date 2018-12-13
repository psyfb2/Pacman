package entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * <p>allows the creation of a 25x25px blue rectangle 
 * represents edges of the maze within the map, within pacman game</p>
 * @author psyfb2
 */
public class BarObstacle extends Rectangle {

    public static final double THICKNESS = 25;
    /**
     * Refactored: removed params orientation and length since every BarOstacle is 25x25px
     * @param x x coordinate
     * @param y y coordinate
     * @param color color of the obstacle e.g. Color.CADETBLUE
     */
    public BarObstacle(double x, double y, Color color) {
        this.setX(x);
        this.setY(y);
        this.setHeight(BarObstacle.THICKNESS);
        this.setWidth(BarObstacle.THICKNESS);
        this.setFill(color);
        this.setStrokeWidth(3);
    }
}
