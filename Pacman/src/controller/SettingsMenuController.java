package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import random.RandomColor;

/**
 * Controls the Settings Menu
 * @author psyfb2
 */
public class SettingsMenuController implements Initializable {
	// Color radio buttons
	@FXML private RadioButton red;
	@FXML private RadioButton green;
	@FXML private RadioButton blue;
	@FXML private RadioButton purple;
	@FXML private RadioButton pink;
	@FXML private RadioButton orange;
	@FXML private RadioButton rainbow;
	@FXML private RadioButton random;
	private Color color = Color.CADETBLUE; // default color is blue
	
	// Difficulty radio buttons
	@FXML private RadioButton easy;
	@FXML private RadioButton medium;
	@FXML private RadioButton hard;
	private String difficulty = "easy"; // default difficulty is easy 
	
	// Map borders for highlighting
	@FXML private BorderPane cookieComplexBorder;
	@FXML private BorderPane ghostTownBorder;
	@FXML private BorderPane greasyGroveBorder;

    private final String cookieComplexFileName = "./recources/maps/cookieComplex.txt";
    private final String cookieComplexScoreFileName = "./recources/scores/cookieComplexScores.txt";
    
    private final String ghostTownFileName = "./recources/maps/ghostTown.txt";
    private final String ghostTownScoreFileName = "./recources/scores/ghostTownScores.txt";
    
    private final String greasyGroveFileName = "./recources/maps/greasyGrove.txt";
    private final String greasyGroveScoreFileName = "./recources/scores/greasyGroveScores.txt";
    
	private String selectedMap = cookieComplexFileName; // cookie complex is selected by default
	private String selectedMapScore = cookieComplexScoreFileName;
	private String mapName = "Cookie Complex";
    
	private ToggleGroup colors;
	private ToggleGroup difficultys;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colors = new ToggleGroup();
		difficultys = new ToggleGroup();
		
		red.setToggleGroup(colors);
		green.setToggleGroup(colors);
		blue.setToggleGroup(colors);
		purple.setToggleGroup(colors);
		pink.setToggleGroup(colors);
		orange.setToggleGroup(colors);
		rainbow.setToggleGroup(colors);
		random.setToggleGroup(colors);
		blue.setSelected(true);
		
		easy.setToggleGroup(difficultys);
		medium.setToggleGroup(difficultys);
		hard.setToggleGroup(difficultys);
		easy.setSelected(true);
		
	}
	
	/**
	 * When this method is called the scene will change to the main menu.
	 * Also passes difficulty and color variable to the main menu controller
	 * @param e Event object generated when the button is clicked
	 * @throws IOException FXML file not found
	 */
	@FXML
	public void menuButtonClicked(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fxml/mainMenu.fxml"));
		Parent root = loader.load();
		
		// pass difficulty and color to the main menu controller
		MainMenuController c = loader.getController();
		c.init(difficulty, selectedMap, selectedMapScore, mapName, color);

		
        Scene settingsMenu = new Scene( root );
        settingsMenu.getStylesheets().add(getClass().getResource("/fxml/style.css").toExternalForm());
        
        Stage theStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
        
        theStage.setScene( settingsMenu );
        theStage.show();
	}
	
	/**
	 * Checks which color radio button is clicked and changes color variable to this.
	 * Difficulty variable is passed to the GameManager later
	 */
	public void colorRadioButtonChanged() {
		if(colors.getSelectedToggle().equals(red)) {
			color = Color.RED;
		}
		if(colors.getSelectedToggle().equals(green)) {
			color = Color.GREEN;
		}
		if(colors.getSelectedToggle().equals(blue)) {
			color = Color.CADETBLUE;
		}
		if(colors.getSelectedToggle().equals(purple)) {
			color = Color.PURPLE;
		}
		if(colors.getSelectedToggle().equals(pink)) {
			color = Color.PINK;
		}
		if(colors.getSelectedToggle().equals(orange)) {
			color = Color.ORANGE;
		}
		if(colors.getSelectedToggle().equals(rainbow)) {
			color = null; // null will indicate to gameManager to use different colored blocks
		}
		if(colors.getSelectedToggle().equals(random)) {
			color = (new RandomColor()).generateRandomColor();
		}
	}
	
	/**
	 * Checks which difficulty radio button is clicked and changes difficulty variable to this.
	 * Color variable is passed to the GameManager later
	 */
	public void difficultyRadioButtonChanged() {
		if(difficultys.getSelectedToggle().equals(easy))  {
			difficulty = "easy";
		}
		if(difficultys.getSelectedToggle().equals(medium))  {
			difficulty = "medium";
		}
		if(difficultys.getSelectedToggle().equals(hard))  {
			difficulty = "hard";
		}
	}
	
	/**
	 * Changes border color around Cookie Complex image to yellow and all other map borders to black (not visible).
	 * Called when Cookie Complex map image is clicked.
	 * @param e Event for the mouse click
	 */
	public void cookieComplexClicked() {
		cookieComplexBorder.setStyle("-fx-border-color: yellow; -fx-border-style: solid; -fx-border-width: 6;");
		ghostTownBorder.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 6;");
		greasyGroveBorder.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 6;");
		selectedMap = cookieComplexFileName;
		selectedMapScore = cookieComplexScoreFileName;
		mapName = "Cookie Complex";
	}
	
	/**
	 * Changes border color around Ghost Town image to yellow and all other map borders to black (not visible).
	 * Called when Ghost Town map image is clicked.
	 * @param e Event for the mouse click
	 */
	public void ghostTownClicked() {
		cookieComplexBorder.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 6;");
		ghostTownBorder.setStyle("-fx-border-color: yellow; -fx-border-style: solid; -fx-border-width: 6;");
		greasyGroveBorder.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 6;");
		selectedMap = ghostTownFileName;
		selectedMapScore = ghostTownScoreFileName;
		mapName = "Ghost Town";
	}
	
	/**
	 * Changes border color around Greasy Grove image to yellow and all other map borders to black (not visible).
	 * Called when Greasy Grove map image is clicked.
	 * @param e Event for the mouse click
	 */
	public void greasyGroveClicked() {
		cookieComplexBorder.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 6;");
		ghostTownBorder.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 6;");
		greasyGroveBorder.setStyle("-fx-border-color: yellow; -fx-border-style: solid; -fx-border-width: 6;");
		selectedMap = greasyGroveFileName;
		selectedMapScore = greasyGroveScoreFileName;
		mapName = "Greasy Grove";
	}
}
