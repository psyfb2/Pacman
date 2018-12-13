package entity;

import javafx.scene.Group;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a Maze. Stores and creates a set of BarObstacle which make up a maze.
 * @author psyfb2
 */
public class Maze { 

	// private to enhance encapsulation
    private Set<BarObstacle> obstacles;
    private Set<BarObstacle> ghostObstacles;

    /**
     * Constructor
     */
    public Maze() {
        obstacles = new HashSet<>();
        ghostObstacles = new HashSet<>();
    }
    
    /**
     * Checks if a point is touching a given BarObstacle Set
	 * @param x x coordinate
     * @param y y coordinate
     * @param padding number of pixels to pad around the given coordinate
     * @return true if the point is touching any obstacles, else false
     */
    private Boolean isTouchingSet(double x, double y, double padding, Set<BarObstacle> obstacles) {
        for (BarObstacle barObstacle:obstacles) {
            if (
                x >= barObstacle.getX() - padding &&
                x <= barObstacle.getX() + padding + barObstacle.getWidth() &&
                y >= barObstacle.getY() - padding &&
                y <= barObstacle.getY() + padding + barObstacle.getHeight())
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a point is touching any of the obstacles (excluding ghost bar obstacles) which make up the maze
     * @param x x coordinate
     * @param y y coordinate
     * @param padding number of pixels to pad around the given coordinate
     * @return true if the point is touching any obstacles, else false
     */
    public Boolean isTouching(double x, double y, double padding) {
        return this.isTouchingSet(x, y, padding, obstacles);
    }
    
    /**
     * Checks if a point is touching any of the obstacles (including ghost bar obstacles) which make up the maze
     * @param x x coordinate
     * @param y y coordinate
     * @param padding number of pixels to pad around the given coordinate
     * @return true if the point is touching any obstacles, else false
     */
    public boolean isTouchingIncludingGhostBarriers(double x, double y, double padding) {
    	return this.isTouchingSet(x, y, padding, obstacles) || this.isTouchingSet(x, y, padding, ghostObstacles);
    }


    /**
     * lets you know if there's an obstacle (including ghost barriers) between two coordinates
     * @param fromX x1 coordinate
     * @param toX x2 coordinate
     * @param fromY y1 coordinate
     * @param toY y2 coordinate
     * @return true if there is an obstacle between (x1, y1) (x2, y2), else false
     */
    public Boolean hasObstacle(double fromX,  double toX, double fromY, double toY) {
        boolean isTouching = false;
        for (double i = fromX; i < toX; i++) {
            for (double j = fromY; j < toY; j++) {
                if (this.isTouchingIncludingGhostBarriers(i, j, 0)) isTouching = true;
            }
        }
        return isTouching;
    }
    
    /**
     * Adds a BarObstacle to the internal BarObstacle Set
     * @param o BarObstacle to add
     */
    public void addObstacle(BarObstacle o) {
    	 this.obstacles.add(o);
    }
    
    /**
     * Adds an invisible barObstacle which is intended to only be visible to ghosts
     * @param o BarObstacle to add
     */
    public void addGhostObstacle(BarObstacle o) {
    	this.ghostObstacles.add(o);
    }
    
    /**
     * Displays all BarObstacles (excluding ghost bar obstacles) contained within the internal BarObstacle set.
     * @param root Root
     */
    public void addAllObstaclesToRoot(Group root) {
    	root.getChildren().addAll(obstacles);
    }
    
    /**
     * Clears internal barObstacle set and ghost barObstacle to contain no obstacles
     */
    public void clearObstacles() {
    	obstacles.clear();
    	ghostObstacles.clear();
    }

	/**
	 * @return internal BarObstacle HashSet
	 */
	public Set<BarObstacle> getObstacles() {
		return obstacles;
	}
}
