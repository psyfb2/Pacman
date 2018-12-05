package manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

import entity.BarObstacle;
import entity.Cookie;
import entity.Ghost;
import entity.Maze;
import entity.Pacman;
import javafx.scene.paint.Color;
import random.RandomColor;

/**
 * Class to load maps from system files
 * @author psyfb2
 */
public class MapLoader {
	BufferedReader mapTxt;
	RandomColor randomColor;
	
	/**
	 * Constructor
	 */
	public MapLoader() {
		randomColor = new RandomColor();
	}
	
	/**
	 * Reads a map from file then configures the maze, cookieSet, ghostSet and pacman to contain the correct information.
	 * Note: this method doesn't actually display anything.
	 * @param fileName Path to the file where the map is saved
	 * @param maze Adds BarObstacles to this
	 * @param cookieSet Adds Cookies to this
	 * @param ghostSet Adds ghosts to this
	 * @param pacman Sets pacman x, y coordinates to the correct position
	 */
	public void loadMap(String fileName, Maze maze, Set<Cookie> cookieSet, Set<Ghost> ghostSet, Pacman pacman, GameManager gameManager) {
		try {
			mapTxt = new BufferedReader(new FileReader(fileName));
			randomColor.reset();
			String line;
			int row = 0;
			// map is split up into 25x25px blocks, the map is 1225x500px
			// so the map is 49x25 blocks
			// read text file line by line, each line representing 49 blocks
			while( (line = mapTxt.readLine()) != null ) {
				for(int column = 0; column < line.length(); column++) {
					switch(line.charAt(column)) {
						case '#':  // wall
							maze.addObstacle(new BarObstacle(BarObstacle.THICKNESS * column, BarObstacle.THICKNESS * row));
							break;
						case '*': // cookie
							// +12.5 because this is the radius of the cookie and we are setting the centre coordinate
							cookieSet.add(new Cookie(BarObstacle.THICKNESS * column + 12.5, BarObstacle.THICKNESS * row + 12.5));
							break;
						case 'p': // pacman
							pacman.setCenterX(BarObstacle.THICKNESS * column + 12.5);
							pacman.setCenterY(BarObstacle.THICKNESS * row + 12.5);
							break;
						case 'g': // ghost
							ghostSet.add(new Ghost(BarObstacle.THICKNESS * (column - 0.5), BarObstacle.THICKNESS * (row - 0.5), randomColor.generateRandomColor(), maze, gameManager));
							break;
					}
				}
				row++;
			}
			mapTxt.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
