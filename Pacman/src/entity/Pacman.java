package entity;



import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Extends Circle
 * Simple class for a yellow circle with radius of 25 pixels
 * @author psyfb2
 */
public class Pacman extends Circle {

    /**
     * Constructor
     * @param x centre x coordinate of the pacman
     * @param y centre y coordinate of the pacman
     */
    public Pacman(double x, double y) {
        this.setCenterX(x);
        this.setCenterY(y);
        this.setRadius(25);
        this.setFill(Color.YELLOW);
    }
}
