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

    /**
     * Constructor
     */
    public Maze() {
        obstacles = new HashSet<>();
    }

    /**
     * Checks if point is touching any of the obstacles which make up the maze
     * @param x x coordinate
     * @param y y coordinate
     * @param padding number of pixels to pad around the given coordinate
     * @return true if the point is touching any obstacles, else false
     */
    public Boolean isTouching(double x, double y, double padding) {
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
     * lets you know if there's an obstacle between two coordinates
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
                if (this.isTouching(i, j, 0)) isTouching = true;
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
     * Displays all BarObstacles contained within the internal BarObstacle set
     * @param root
     */
    public void addAllObstaclesToRoot(Group root) {
    	root.getChildren().addAll(obstacles);
    }
    
    /**
     * Clears internal barObstacle set to contain no obstacles
     */
    public void clearObstacles() {
    	obstacles.clear();
    }
}
