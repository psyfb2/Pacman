package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * <p>allows the creation of a blue rectangle which is either horizontal or vertical
 * for horizontal rectangle width = length * BarObstacle.THICKNESS, height = BarObstacle.THICKNESS
 * for vertical triangle width = BarObstacle.THICKNESS, height = length * BarObstacle.THICKNESS
 * represents edges of the map within pacman game</p>
 * @author psyfb2
 */
public class BarObstacle extends Rectangle {

    public static double THICKNESS = 25;
    /**
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param orientation horizontal or vertical
     * @param length the length of the bar (1 == 100px)
     */
    public BarObstacle(double x, double y, String orientation, double length) {
        this.setX(x);
        this.setY(y);
        if (orientation.equals("horizontal")) {
            this.setHeight(BarObstacle.THICKNESS);
            this.setWidth(length * BarObstacle.THICKNESS);
        } else {
            this.setHeight(length * BarObstacle.THICKNESS);
            this.setWidth(BarObstacle.THICKNESS);
        }
        this.setFill(Color.CADETBLUE);
        this.setStrokeWidth(3);
    }
}
