package manager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
	private BufferedReader mapTxt;
	private int maxNumberOfGhosts;
	private int ghostCounter;
	private String ghostImageName;
	
	/**
	 * Default Constructor, maxNumberOfGhosts is 5, ghostImageName = "./recources/images/ghost".
	 */
	public MapLoader() {
		this.maxNumberOfGhosts = 5;
		this.ghostImageName = "./recources/images/ghost";
	}
	
	/**
	 * Constructor
	 * @param ghostImageName File name for ghost images excluding the ghost number. E.g. "./recources/images/ghost" will cause "./recources/images/ghost0.png", "./recources/images/ghost1.png", etc to get loaded
	 * @param maxNumberOfGhosts How many ghost images there are with the base name given in ghostImageName variable.
	 * Ghost images should also be .png files.
	 */
	public MapLoader(int maxNumberOfGhosts, String ghostImageName) {
		this.maxNumberOfGhosts = maxNumberOfGhosts;
		this.ghostImageName = ghostImageName;
	}
	
	/**
	 * Reads a map from file then configures the maze, cookieSet, ghostSet and pacman to contain the correct information. 
	 * Also encodes the map if it is not encoded using Run Length Encoding.
	 * Note: this method doesn't actually display anything.
	 * @param fileName Path to the file where the map is saved
	 * @param maze Maze to store new BarObstacle objects 
	 * @param cookieSet CookieSet to store new Cookie objects 
	 * @param ghostSet GhostSet to store new Ghost objects
	 * @param pacman Pacman to set it's x and y position
	 * @param gameManager GameManager in which the entities are aggregated
	 * @param ghostSpeed Speed of the ghosts (e.g. 5)
	 * @param mazeColor Color of the BarObstacle objects which are added to the maze, null for a random color for each BarObstacle
	 * @throws IOException File name not found or ghostImageName+ghostCounter provided in constructor not found
	 */
	public void loadMap(String fileName, Maze maze, Set<Cookie> cookieSet, Set<Ghost> ghostSet, Pacman pacman, GameManager gameManager, int ghostSpeed, Color mazeColor) throws IOException {
		if(!MapEncoder.isEncoded(fileName)) {
			MapEncoder.RunLengthEncode(fileName);
		}
		mapTxt = new BufferedReader(new FileReader(fileName));
		String line;
		int row = 0;
		int adjacentChars = 0; 
		ghostCounter = 0;

		// map is split up into 25x25px blocks, the map is 1225x500px
		// so the map is 49x25 blocks
		// read text file line by line, each line representing 49 blocks
		
		while( (line = mapTxt.readLine()) != null ) {
			int column = 0;
			for(int i = 0; i < line.length(); i++) {
				char c = line.charAt(i);
				if(Character.isDigit(c)) {
					adjacentChars = adjacentChars * 10 + c - '0';
				} 
				else { // c is a non digit character
					while(adjacentChars > 0) {
							charToEntity(c, column , row, maze, cookieSet, ghostSet, pacman, gameManager, ghostSpeed, mazeColor);
							adjacentChars--;
							column++;
						}
				}
			}
			row++;
		}	
	}
	
	/**
	 * Given a Character this method will create a new entity corresponding to the character.
	 * Essentially the factory pattern for producing entities at runtime based on an input character.
	 * @param c Input character
	 * @param column Column position of the entity on the map (0-49)
	 * @param row Row position of the entity on the map (0-25)
	 * @param maze Maze to store new BarObstacle objects 
	 * @param cookieSet CookieSet to store new Cookie objects 
	 * @param ghostSet GhostSet to store new Ghost objects
	 * @param pacman Pacman to set it's x and y position
	 * @param gameManager GameManager in which the entities are aggregated
	 * @param ghostSpeed Speed of the ghosts (e.g. 5)
	 * @param mazeColor Color of the BarObstacle objects which are added to the maze, null for a random color for each BarObstacle
	 * @throws FileNotFoundException ghostImageName+ghostCounter was not found. Default ghostImageName is "./recources/images/ghost" and ghostCounter is appended to load different ghost images.
	 */
	private void charToEntity(char c, int column, int row, Maze maze, Set<Cookie> cookieSet, Set<Ghost> ghostSet, Pacman pacman, GameManager gameManager, int ghostSpeed, Color mazeColor) throws FileNotFoundException {
		switch(c) {
			case '#':  // wall
				if(maze != null) {
					if(mazeColor == null) { // null means each block should have a random color
						Color randomColor = (new RandomColor()).generateRandomColor();
						maze.addObstacle(new BarObstacle(BarObstacle.THICKNESS * column, BarObstacle.THICKNESS * row, randomColor));
					}
					else {
						maze.addObstacle(new BarObstacle(BarObstacle.THICKNESS * column, BarObstacle.THICKNESS * row, mazeColor));
					}
				}
				break;
				
			case '*': // cookie
				// +12.5 because this is the radius of the cookie and we are setting the centre coordinate
				if(cookieSet != null) {
					cookieSet.add(new Cookie(BarObstacle.THICKNESS * column + 12.5, BarObstacle.THICKNESS * row + 12.5));
				}
				break;
				
			case 'p': // pacman
				if(pacman != null) {
					pacman.setCenterX(BarObstacle.THICKNESS * column + 12.5);
					pacman.setCenterY(BarObstacle.THICKNESS * row + 12.5);
					pacman.setInitialPositionX(BarObstacle.THICKNESS * column + 12.5);
					pacman.setInitialPositionY(BarObstacle.THICKNESS * row + 12.5);
				}
				break;
				
			case 'g': // ghost
				if(ghostSet != null) {
					ghostSet.add(new Ghost(BarObstacle.THICKNESS * (column - 0.5), BarObstacle.THICKNESS * (row - 0.5), maze, gameManager, ghostImageName + ghostCounter + ".png", ghostSpeed));
					ghostCounter = (ghostCounter + 1) % maxNumberOfGhosts;
				}
				break;
				
			case ':': // ghost bar obstacle (bar obstacle only stops the ghost and is invisible)
				if(maze != null) {
					maze.addGhostObstacle(new BarObstacle(BarObstacle.THICKNESS * column, BarObstacle.THICKNESS * row, Color.WHITE));
				}
				break;
		}
	}
}
