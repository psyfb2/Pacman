package entity;



import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


/**
 * <p>Creates a brown circle with radius 12.5.
 * The cookie also has an associated value which can represent score
 * increase each time a cookie is consumed.</p>
 * @author psyfb2
 */
public class Cookie extends Circle {

    private int value; // score increased by this value each time circle is consumed

    /**
     * @param x x coordinate
     * @param y y coordinate
     */
    public Cookie(double x, double y) {
        this.value = 5;
        this.setCenterX(x);
        this.setCenterY(y);
        this.setRadius(12.5);
        this.setFill(Color.SADDLEBROWN);
    }

    /**
     * @return value which represents score increase each time a cookie is consumed
     */
    public int getValue() {
        return value;
    }

    /**
     * hide the cookie so it is no longer displayed
     */
    public void hide() {
        this.setVisible(false);
    }
    
    /**
     * display the cookie 
     */
    public void show() {
        this.setVisible(true);
    }

}