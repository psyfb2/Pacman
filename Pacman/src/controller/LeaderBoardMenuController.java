package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import leaderboard.LeaderBoard;

/**
 * Controls the LeaderBoard pop up menu
 * @author psyfb2
 */
public class LeaderBoardMenuController implements Initializable {
	@FXML private Label mapNameLabel;
	@FXML private Label playerScoreLabel;
	@FXML private Label difficultyLabel;
	@FXML private TextField playerName;
	
	@FXML private Label nameLabel1;
	@FXML private Label nameLabel2;
	@FXML private Label nameLabel3;
	@FXML private Label nameLabel4;
	@FXML private Label nameLabel5;
	
	@FXML private Label scoreLabel1;
	@FXML private Label scoreLabel2;
	@FXML private Label scoreLabel3;
	@FXML private Label scoreLabel4;
	@FXML private Label scoreLabel5;
	
	@FXML private Button addScoreButton;
	
	private LeaderBoard leaderBoard;
	private int score;
	private Label[] nameLabels = new Label[5];
	private Label[] scoreLabels = new Label[5];
	
	/**
	 * Attempt to add the current score to the score board and save it.
	 * If the score is not within the top 5 scores then the score will not be added.
	 * Note: this button can only be clicked once, after first time click the button will be disabled.
	 * @param e Mouse click ActionEvent
	 */
	@FXML
	public void addScoreButtonClicked(ActionEvent e) {
		addScoreButton.setDisable(true);
		
		// Remove any escape characters from user input as these can cause issues
		String playerNameValidated = playerName.getText().replace("\\", "");
		if(playerNameValidated.equals("")) {
			playerNameValidated = "Mr. Noname";
		}
		leaderBoard.addToLeaderBoard(playerNameValidated, score);
		
		displayLeaderBoard();
		
		try {
			leaderBoard.saveLeaderBoard();
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("Error: failed to save LeaderBoard");
		}
	}
	
	/**
	 * Change text values within the labels to mirror the leaderBoard object given in the setleaderBoard() method.
	 */
	private void displayLeaderBoard() {
		for(int i = 0; i < leaderBoard.length() && i < 5; i++) {
			nameLabels[i].setText(leaderBoard.getNameFromPosition(i + 1));
			scoreLabels[i].setText(Integer.toString(leaderBoard.getScoreFromPosition(i + 1)));
		}
	}
	
	/**
	 * Use this method to pass data to this controller.
	 * @param leaderBoard Leaderboard object to load, save and add scores.
	 * @param score Score which the user got.
	 * @param mapName The name of the map the user just map. Note not a file name just a name to display to the user e.g. "Cookie Complex"
	 * @param difficulty The difficulty the user played on. This is needed so it can be displayed to the user.
	 */
	public void init(LeaderBoard leaderBoard, int score, String mapName, String difficulty) {
		this.leaderBoard = leaderBoard;
		this.score = score;
		
		// Capitalise first letter
		if(difficulty != null && difficulty.length() != 0) {
			difficulty = difficulty.substring(0, 1).toUpperCase() + difficulty.substring(1);
		}
		
		playerScoreLabel.setText(Integer.toString(score));
		mapNameLabel.setText(mapName);
		difficultyLabel.setText(difficulty);
		
		this.displayLeaderBoard();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		nameLabels[0] = nameLabel1;
		nameLabels[1] = nameLabel2;
		nameLabels[2] = nameLabel3;
		nameLabels[3] = nameLabel4;
		nameLabels[4] = nameLabel5;
		
		scoreLabels[0] = scoreLabel1;
		scoreLabels[1] = scoreLabel2;
		scoreLabels[2] = scoreLabel3;
		scoreLabels[3] = scoreLabel4;
		scoreLabels[4] = scoreLabel5;
		
	}
}
