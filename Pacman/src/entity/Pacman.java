package entity;



import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents pacman as an image
 * @author psyfb2
 */
public class Pacman {
	private double initialPositionX;
	private double initialPositionY;
	private double radius;
	private ImageView imageView;
	
    /**
     * Constructor
     * @param x centre x coordinate of the pacman
     * @param y centre y coordinate of the pacman
     * @param radius radius of the pacman
     * @param fileName file name for the pacman image
     * @throws FileNotFoundException fileName was not found
     */
    public Pacman(double x, double y, double radius, String fileName) throws FileNotFoundException {
    	Image image = new Image(new FileInputStream(fileName));
    	imageView = new ImageView(image);
    	this.initialPositionX = x;
    	this.initialPositionY = y;
    	this.radius = radius;
    	this.setCenterX(x);
    	this.setCenterY(y);
    	imageView.setFitWidth(radius*2);
    	imageView.setFitHeight(radius*2);
    	imageView.setPreserveRatio(true);  
    }
    
    /**
     * Changes position of the pacman to the (x, y) coordinate to initial x, y positions
     */
    public void resetPosition() {
    	this.setCenterX(initialPositionX);
    	this.setCenterY(initialPositionY);
    }
    
    /**
     * Adds pacman onto the root (to display the pacman)
     * @param root add the pacman to this
     */
    public void displayPacman(Group root) {
    	root.getChildren().add(imageView);
    }
    
    /**
     * Removes pacman from the root (removed pacman from the display)
     * @param root add the pacman to this
     */
    public void unDisplayPacman(Group root) {
    	root.getChildren().remove(imageView);
    }
    
    /**
     * Sets the centre x coordinate of the pacman
     * @param x x coordinate
     */
    public void setCenterX(double x) {
    	imageView.setX(x - radius);
    }
    
    /**
     * Sets the centre y coordinate of the pacman
     * @param y y coordinate
     */
    public void setCenterY(double y) {
    	imageView.setY(y - radius);
    }
    
    /**
     * Gets the centre x coordinate of the pacman
     * @return centre x coordinate of the pacman
     */
    public double getCenterX() {
    	return imageView.getX() + radius;
    }
    
    /**
     * Gets the centre y coordinate of the pacman
     * @return y coordinate of the pacman
     */
    public double getCenterY() {
    	return imageView.getY() + radius;
    }
    
    /**
     * Gets the radius of the pacman
     * @return radius of the pacman
     */
    public double getRadius() {
    	return radius;
    }
    
    /**
     * Rotate the pacman image so it's mouth is facing right (this is the defualt position)
     */
    public void facePacmanRight() {
    	imageView.setRotate(0);
    }
    
    /**
     * Rotate the pacman image so it's mouth is facing down
     */
    public void facePacmanDown() {
    	imageView.setRotate(90);
    }
    
    /**
     * Rotate the pacman image so it's mouth is facing left
     */
    public void facePacmanLeft() {
    	imageView.setRotate(180);
    }
    
    /**
     * Rotate the pacman image so it's mouth is facing up
     */
    public void facePacmanUp() {
    	imageView.setRotate(270);
    }

	/**
	 * Set the initial pacman x position, when reset() method is called x position will be changed to initialPositionX
	 * @param initialPositionX Initial x position on the screen
	 */
	public void setInitialPositionX(double initialPositionX) {
		this.initialPositionX = initialPositionX;
	}
	
	/**
	 * Set the initial pacman y position, when reset() method is called y position will be changed to initialPositionY
	 * @param initialPositionY Initial y position on the screen
	 */
	public void setInitialPositionY(double initialPositionY) {
		this.initialPositionY = initialPositionY;
	}
}
