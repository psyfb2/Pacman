package entity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import manager.GameManager;
import random.RandomDirection;

/**
 * Represents a ghost within the pacman game. 
 * Also has run method to animate the ghost within the maze.
 * @author psyfb2
 */
public class Ghost implements Runnable {

	// private to enforce encapsulation
    private String direction;
    private GameManager gameManager;
    private Maze maze;
    private AnimationTimer animation;
    private int timesWalked;
    private RandomDirection rand;
    private ImageView imageView;
    
    /**
     * Constructor
     * @param x x coordinate
     * @param y y coordinate
     * @param color colour of the ghost
     * @param maze maze in which the ghost will be in
     * @param gameManager gameManager in which the ghost will be in
     * @throws FileNotFoundException if fileName is not found
     */
    public Ghost(double x, double y, Maze maze, GameManager gameManager, String fileName) throws FileNotFoundException {
    	Image image = new Image(new FileInputStream(fileName));
    	imageView = new ImageView(image);
    	imageView.setFitWidth(50);
    	imageView.setFitHeight(50);
        this.setX(x);
        this.setY(y);
        this.maze = maze;
        this.gameManager = gameManager;
        this.timesWalked = 0;
        this.direction = "down";
        this.rand = new RandomDirection();
        this.createAnimation();
    }

	/**
     * Gets the animation for the ghost
     * @return animation
     */
    public AnimationTimer getAnimation() {
        return animation;
    }

    /**
     * Checks if there is a clear path in a given direction. If so set this.direct = direction.
     * Used within createAnimation() method
     * @param direction direction to check for a clear path
     */
    private void checkIftheresPathToGo(String direction) {
        double rightEdge, leftEdge, topEdge, bottomEdge;
        switch (direction) {
            case "down":
                leftEdge = getX() - 10;
                bottomEdge = getY() + getHeight() + 15;
                rightEdge = getX() + getWidth() + 10;
                if (!maze.hasObstacle(leftEdge, rightEdge, bottomEdge - 1, bottomEdge)) {
                    this.direction = direction;
                }
                break;
            case "up":
                leftEdge = getX() - 10;
                rightEdge = getX() + getWidth() + 10;
                topEdge = getY() - 15;
                if (!maze.hasObstacle(leftEdge, rightEdge, topEdge - 1, topEdge)) {
                    this.direction = direction;
                }
                break;
            case "left":
                leftEdge = getX() - 15;
                bottomEdge = getY() + getHeight() + 10;
                topEdge = getY() - 10;
                if (!maze.hasObstacle(leftEdge - 1, leftEdge, topEdge, bottomEdge)) {
                    this.direction = direction;
                }
                break;
            case "right":
                bottomEdge = getY() + getHeight() + 10;
                rightEdge = getX() + getWidth() + 15;
                topEdge = getY() - 10;
                if (!maze.hasObstacle(rightEdge - 1, rightEdge, topEdge, bottomEdge)) {
                    this.direction = direction;
                }
                break;
        }
    }

	/**
     * Moves the ghost in a direction, if the maze is blocking this direction
     * then move in the opposite direction until the ghost is obstructed by the maze.
     * Then set the direction to whereToChangeTo.
     * @param whereToGo direction in which the ghost should try to move, either "left", "right", "up", "down"
     * @param whereToChangeTo direction the ghost should move towards, "left", "right", "up", "down"
     * @param leftEdge left x coordinate of the ghost
     * @param topEdge top y coordinate of the ghost
     * @param rightEdge right x coordinate of the ghost
     * @param bottomEdge bottom y coordinate of the ghost
     * @param padding number of pixels to pad around the ghost when checking for collisions
     */
    private void moveUntilYouCant(String whereToGo, String whereToChangeTo, double leftEdge, double topEdge, double rightEdge, double bottomEdge, double padding) {
        double step = 5;
        switch (whereToGo) {
            case "left":
            	// if the maze is not touching the left of the ghost then move ghost to the left by 5 pixels
                if (!maze.isTouching(leftEdge, topEdge, padding)) {
                    setX(leftEdge - step);
                } else {
                	// else move ghost to the right by 1 pixel until it touches the maze
                    while (maze.isTouching(getX(), getY(), padding)) {
                        setX(getX() + 1); 
                    }
                    // when ghost touches the maze change its direction
                    direction = whereToChangeTo;
                }
                break;
            case "right":
                if (!maze.isTouching(rightEdge, topEdge, padding)) {
                    setX(leftEdge + step);
                } else {
                    while (maze.isTouching(getX() + getWidth(), getY(), padding)) {
                        setX(getX() - 1);
                    }
                    direction = whereToChangeTo;
                }
                break;
            case "up":
                if (!maze.isTouching(leftEdge, topEdge, padding)) {
                    setY(topEdge - step);
                } else {
                    while (maze.isTouching(getX(), getY(), padding)) {
                        setY(getY() + 1);
                    }
                    direction = "left";
                }
                break;
            case "down":
                if (!maze.isTouching(leftEdge, bottomEdge, padding)) {
                    setY(topEdge + step);
                } else {
                    while (maze.isTouching(getX(), getY() + getHeight(), padding)) {
                        setY(getY() - 1);
                    }
                    direction = "right";
                }
                break;
        }

    }

    /**
     * Creates the animation of the ghost, called as part of the constructor
     */
    public void createAnimation() {

        this.animation = new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                gameManager.checkGhostCoalition();
                double leftEdge = getX();
                double topEdge = getY();
                double rightEdge = getX() + getWidth();
                double bottomEdge = getY() + getHeight();
                double padding = 12;
                timesWalked++;
                int walkAtLeast = 4;
                switch (direction) {
                    case "left":
                        moveUntilYouCant("left", "down", leftEdge, topEdge, rightEdge, bottomEdge, padding);
                        if (timesWalked > walkAtLeast) {
                            checkIftheresPathToGo(rand.getRandomDirection("left", "right"));
                            timesWalked = 0;
                        }
                        break;
                    case "right":
                        moveUntilYouCant("right", "up", leftEdge, topEdge, rightEdge, bottomEdge, padding);
                        if (timesWalked > walkAtLeast) {
                            checkIftheresPathToGo(rand.getRandomDirection("left", "right"));
                             timesWalked = 0;
                        }
                        break;
                    case "up":
                        moveUntilYouCant("up", "left", leftEdge, topEdge, rightEdge, bottomEdge, padding);
                        if (timesWalked > walkAtLeast) {
                            checkIftheresPathToGo(rand.getRandomDirection("up", "down"));
                            timesWalked = 0;
                        }
                        break;
                    case "down":
                        moveUntilYouCant("down", "right", leftEdge, topEdge, rightEdge, bottomEdge, padding);
                        if (timesWalked > walkAtLeast) {
                            checkIftheresPathToGo(rand.getRandomDirection("up", "down"));
                            timesWalked = 0;
                        }
                        break;
                }
            }
        };
    }


    @Override
    public void run() {
        this.animation.start();
    }
    
    public void displayGhost(Group root) {
    	root.getChildren().add(imageView);
    }
    
    public void unDisplayGhost(Group root) {
    	root.getChildren().remove(imageView);
    }
    
    /**
     * @return Height in pixels of the ghost 
     */
    public double getHeight() {
		return imageView.getFitHeight();
	}

	/**
	 * @return Width in pixels of the ghost
	 */
	public double getWidth() {
		return imageView.getFitWidth();
	}

	/**
	 * @return Y coordinate of the ghost
	 */
	public double getY() {
		return imageView.getY();
	}

	/**
	 * @return X coordinate of the ghost
	 */
	public double getX() {
		return imageView.getX();
	}
	
    /**
     * @param y Set Y coordinate of the ghost to this
     */
    public void setY(double y) {
		imageView.setY(y);
		
	}

	/**
	 * @param x Set X coordinate of the ghost to this
	 */
	public void setX(double x) {
		imageView.setX(x);
		
	}
}
