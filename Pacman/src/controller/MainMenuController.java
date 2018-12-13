package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import manager.GameManager;

/**
 * Controls the Main Menu
 * @author psyfb2
 */
public class MainMenuController {
	// default values
	private String difficulty = "easy";
	private Color color = Color.CADETBLUE;
	private String mapFileName = "./recources/maps/cookieComplex.txt";
	private String scoreFileName = "./recources/scores/cookieComplexScores.txt";
	private String mapName = "Cookie Complex";
	
	/**
	 * When this method is called the scene will be changed to the pacman game.
	 * @param e Event object generated when the button is clicked
	 */
	@FXML 
	public void playButtonClicked(ActionEvent e) {
		
		Group root = new Group();
	
        GameManager gameManager;
		
		gameManager = new GameManager(root, difficulty, color, mapFileName, scoreFileName, mapName);
    	
    	Scene pacmanGame = new Scene(root);
    	
        pacmanGame.addEventHandler(KeyEvent.KEY_PRESSED, event -> gameManager.movePacman(event));
        pacmanGame.addEventHandler(KeyEvent.KEY_RELEASED, event -> gameManager.stopPacman(event));
        pacmanGame.addEventHandler(KeyEvent.KEY_PRESSED, event -> gameManager.restartGame(event));
        
        gameManager.drawBoard();
        
        // now we have created the scene for the pacman game we need to add it to the stage created in main
        Stage theStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
        
        theStage.setScene(pacmanGame);
        theStage.show();
	}
	
	/**
	 * When this method is called the scene will change to the settings menu
	 * @param e Event object generated when the button is clicked
	 * @throws IOException FXML file not found
	 */
	@FXML
	public void settingsButtonClicked(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/settingsMenu.fxml"));
        Scene settingsMenu = new Scene( root );
        settingsMenu.getStylesheets().add(getClass().getResource("/fxml/style.css").toExternalForm());
        
        Stage theStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
        
        theStage.setScene( settingsMenu );
        theStage.show();
	}

	/**
	 * Call this method to pass data to this controller.
	 * @param difficulty Difficulty of the pacman game, "easy", "medium" or "hard".
	 * @param fileName File name of the map to load (e.g. "./recources/maps/cookieComplex.txt")
	 * @param scoreFileName File name of the leaderboard for the provided map
	 * @param mapName Name of the map (not a file name just a name to display to user)
	 * @param color Color which the maze should have when the pacman game starts
	 */
	public void init(String difficulty, String mapFileName, String scoreFileName, String mapName, Color color) {
		this.difficulty = difficulty;
		this.mapFileName = mapFileName;
		this.scoreFileName = scoreFileName;
		this.mapName = mapName;
		this.color = color;
	}
}
