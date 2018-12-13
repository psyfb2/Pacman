package manager;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import leaderboard.LeaderBoard;
import leaderboard.PacmanLeaderBoard;
import main.Main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import controller.LeaderBoardMenuController;
import entity.*;

/**
 * Provides methods to draw, manage and animate the game.
 * @author psyfb2
 */
public class GameManager {
    private Pacman pacman;
    private Group root;
    private Set<Cookie> cookieSet;
    private Set<Ghost> ghosts;
    private AnimationTimer leftPacmanAnimation;
    private AnimationTimer rightPacmanAnimation;
    private AnimationTimer upPacmanAnimation;
    private AnimationTimer downPacmanAnimation;
    private Maze maze;
    private int lifes;
    private int score;
    private Score scoreBoard;
    private boolean gameEnded;
    private int cookiesEaten;
    private MapLoader mapLoader;
    private int pacmanSpeed = 5;
    private int ghostSpeed = 5;
    private Color mazeColor;
    private String mapFileName;
    private String scoreFileName;
    private String mapName;
    private String difficulty;
    private final String pacmanFileName = "./recources/images/pacman.png";

    /**
     * Constructor to initialise gameManager
     * @param root Used to display entities
     * @param difficulty "easy", "medium" or "hard"
     * @param mazeColor Color of BarObstacles within the maze
     * @param mapFileName File where the map is stored
     * @param scoreFileName File where the scoreboard for the map can be found
     * @param mapName Name of the map. Not a file name just a name for the map so this can be displayed to the user (e.g. "Cookie Complex") 
     */
    public GameManager(Group root, String difficulty, Color mazeColor, String mapFileName, String scoreFileName, String mapName) {
        this.root = root;
        this.maze = new Maze();
        try {
			this.pacman = new Pacman(2.5 * BarObstacle.THICKNESS, 2.5 * BarObstacle.THICKNESS, 25, pacmanFileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        this.cookieSet = new HashSet<>();
        this.ghosts = new HashSet<>();
        this.leftPacmanAnimation = this.createAnimation("left");
        this.rightPacmanAnimation = this.createAnimation("right");
        this.upPacmanAnimation = this.createAnimation("up");
        this.downPacmanAnimation = this.createAnimation("down");
        this.lifes = 3;
        this.score = 0;
        this.cookiesEaten = 0;
        this.mapLoader = new MapLoader();
        this.difficulty = difficulty;
        this.setSpeed(difficulty);
        this.mazeColor = mazeColor;
        this.mapFileName = mapFileName;
        this.scoreFileName = scoreFileName;
        this.mapName = mapName;
    }
    
    /**
     * easy - pacmanSpeed = 5, ghostSpeed = 5
     * medium - pacmanSpeed = 4, ghostSpeed = 6
     * hard - pacmanSpeed = 4, ghostSpeed = 8
     * @param difficulty "easy", "medium" or "hard".
     */
    private void setSpeed(String difficulty) {
    	if(difficulty.equals("easy")) {
    		pacmanSpeed = 5;
    		ghostSpeed = 5;
    	}
    	if(difficulty.equals("medium")) {
    		pacmanSpeed = 4;
    		ghostSpeed = 6;
    	}
    	if(difficulty.equals("hard")) {
    		pacmanSpeed = 4;
    		ghostSpeed = 8;
    	}
    }

    /**
     * Set one life less, 
     * stops all animations,
     * reposition pacman to topleft,
     * decrease score by 10,
     * updates scoreBoard text and ends the game if lifes == 0
     */
    private void lifeLost() {
        this.leftPacmanAnimation.stop();
        this.rightPacmanAnimation.stop();
        this.upPacmanAnimation.stop();
        this.downPacmanAnimation.stop();
        for (Ghost ghost : ghosts) {
            ghost.getAnimation().stop();
        }
        this.pacman.resetPosition();
        lifes--;
        score -= 10;
        this.scoreBoard.getLifes().setText("Lifes: " + this.lifes);
        this.scoreBoard.getScore().setText("Score: " + this.score);
        if (lifes == 0) {
            this.endGame();
        }
    }

    /**
     * Ends the game
     */
    private void endGame() {
        this.gameEnded = true;
        pacman.unDisplayPacman(root);
        for (Ghost ghost : ghosts) {
            ghost.unDisplayGhost(root);
        }
        javafx.scene.text.Text endGame = new javafx.scene.text.Text("Game Over, press ESC to restart");
        endGame.setX(BarObstacle.THICKNESS * 3);
        endGame.setY(BarObstacle.THICKNESS * 28);
        endGame.setFont(Font.font("Arial", 40));
        endGame.setFill(Color.ROYALBLUE);
        root.getChildren().remove(this.scoreBoard.getScore());
        root.getChildren().remove(this.scoreBoard.getLifes());
        root.getChildren().add(endGame);
		loadLeaderBoard();
    }
    
    /**
     * Creates new pop up window for the leaderboard.
     * Score passed to the pop up window is influenced by the difficulty. "easy" -> score, "medium" -> score*2, "hard" -> score*4.
     * @throws IOException "/fxml/mainMenu.fxml" not found or scoreFileName not found
     */
    private void loadLeaderBoard() {
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fxml/scoreBoard.fxml"));
		Parent root;
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to load /fxml/scoreBoard.fxml");
			return;
		}
		
		LeaderBoard leaderBoard;
		try {
			leaderBoard = new PacmanLeaderBoard(scoreFileName, 5);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to load " + scoreFileName);
			return;
		}
		
		LeaderBoardMenuController c = loader.getController();
		if(difficulty.equals("medium")) {
			score = score << 1;
		}
		else if(difficulty.equals("hard")) {
			score = score << 2;
		}
		c.init(leaderBoard, score, mapName, difficulty);
		
		Stage popUp = new Stage();
		Scene popUpScene = new Scene(root);
		popUpScene.getStylesheets().add(getClass().getResource("/fxml/style.css").toExternalForm());
		popUp.setScene(popUpScene);
		popUp.show();
    }

    /**
     * If the game has ended and user presses escape character then restart the game
     * @param event key press
     */
    public void restartGame(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE && gameEnded) {
            root.getChildren().clear();
            this.cookieSet.clear();
            this.ghosts.clear();
            maze.clearObstacles();
            this.drawBoard();
            this.lifes = 3;
            this.score = 0;
            this.cookiesEaten = 0;
            gameEnded = false;
        }
    }

    /**
     * Draws the board of the game with the cookies and the Pacman
     */
    public void drawBoard() {
    	try {
			mapLoader.loadMap(mapFileName, maze, cookieSet, ghosts, pacman, this, ghostSpeed, mazeColor);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Failed to load file " + mapFileName);
			return;
		}
        maze.addAllObstaclesToRoot(root);
    	root.getChildren().addAll(cookieSet);
        pacman.displayPacman(root);
        for(Ghost ghost : ghosts) {
        	ghost.displayGhost(root);
        }
        this.scoreBoard = new Score(root);
        //root.getChildren().add(toMainMenu);
    }

    /**
     * Moves the pacman left, right, up or down and moves all ghosts.
     * Also everytime pacman moves there is a 1% chance of a speed cookie spawning
     * @param event key press
     */
    public void movePacman(KeyEvent event) {
        for (Ghost ghost : this.ghosts) {
            ghost.run();
        }
        switch(event.getCode()) {
            case RIGHT:
                this.rightPacmanAnimation.start();
                break;
            case LEFT:
                this.leftPacmanAnimation.start();
                break;
            case UP:
                this.upPacmanAnimation.start();
                break;
            case DOWN:
                this.downPacmanAnimation.start();
                break;
			default:
				break;
        }
    }

    /**
     * Stops the pacman
     * @param event key press
     */
    public void stopPacman(KeyEvent event) {
        switch(event.getCode()) {
            case RIGHT:
                this.rightPacmanAnimation.stop();
                break;
            case LEFT:
                this.leftPacmanAnimation.stop();
                break;
            case UP:
                this.upPacmanAnimation.stop();
                break;
            case DOWN:
                this.downPacmanAnimation.stop();
                break;
			default:
				break;
        }
    }

    /**
     * Creates an animation of the movement of the pacman.
     * @param direction direction in which to move the pacman, either "up", "down", "left" or "right"
     * @return AnimationTimer to move the pacman 5 pixels in the specified direction, takes into account wall, cookie and ghost collisions
     */
    private AnimationTimer createAnimation(String direction) {
        double step = pacmanSpeed;
        return new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
            checkPacmanEnterDoor();
            switch (direction) {
                case "left":
                    if (!maze.isTouching(pacman.getCenterX() - pacman.getRadius(), pacman.getCenterY(), 15)) {
                        pacman.setCenterX(pacman.getCenterX() - step);
                        pacman.facePacmanLeft();
                        checkCookieCoalition(pacman, "x");
                        checkGhostCoalition();
                    }
                    break;
                case "right":
                    if (!maze.isTouching(pacman.getCenterX() + pacman.getRadius(), pacman.getCenterY(), 15)) {
                        pacman.setCenterX(pacman.getCenterX() + step);
                        pacman.facePacmanRight();
                        checkCookieCoalition(pacman, "x");
                        checkGhostCoalition();
                    }
                    break;
                case "up":
                    if (!maze.isTouching(pacman.getCenterX(), pacman.getCenterY() - pacman.getRadius(), 15)) {
                        pacman.setCenterY(pacman.getCenterY() - step);
                        pacman.facePacmanUp();
                        checkCookieCoalition(pacman, "y");
                        checkGhostCoalition();
                    }
                    break;
                case "down":
                   if (!maze.isTouching(pacman.getCenterX(), pacman.getCenterY() + pacman.getRadius(), 15)) {
                       pacman.setCenterY(pacman.getCenterY() + step);
                       pacman.facePacmanDown();
                       checkCookieCoalition(pacman, "y");
                       checkGhostCoalition();
                   }
                   break;
            }
            }
        };
    }

    /**
     * Checks if the pacman touches cookies. If so then increase score by cookie.getValue(), increment cookiesEaten and hide the cookie.
     * Then checks if all the cookies have been eaten. If so then end the game.
     * @param pacman
     * @param axis "x" to check cookies in horizontal direction (right and left), else check cookies in vertical direction (up and down)
     */
    private void checkCookieCoalition(Pacman pacman, String axis) {
        double pacmanCenterY = pacman.getCenterY();
        double pacmanCenterX = pacman.getCenterX();
        double pacmanLeftEdge = pacmanCenterX - pacman.getRadius();
        double pacmanRightEdge = pacmanCenterX + pacman.getRadius();
        double pacmanTopEdge = pacmanCenterY - pacman.getRadius();
        double pacmanBottomEdge = pacmanCenterY + pacman.getRadius();
        for (Cookie cookie:cookieSet) {
            double cookieCenterX = cookie.getCenterX();
            double cookieCenterY = cookie.getCenterY();
            double cookieLeftEdge = cookieCenterX - cookie.getRadius();
            double cookieRightEdge = cookieCenterX + cookie.getRadius();
            double cookieTopEdge = cookieCenterY - cookie.getRadius();
            double cookieBottomEdge = cookieCenterY + cookie.getRadius();
            if (axis.equals("x")) {
                // pacman goes right
                if ((cookieCenterY >= pacmanTopEdge && cookieCenterY <= pacmanBottomEdge) && (pacmanRightEdge >= cookieLeftEdge && pacmanRightEdge <= cookieRightEdge)) {
                    if (cookie.isVisible()) {
                        this.score += cookie.getValue();
                        this.cookiesEaten++;
                    }
                    cookie.hide();
                }
                // pacman goes left
                if ((cookieCenterY >= pacmanTopEdge && cookieCenterY <= pacmanBottomEdge) && (pacmanLeftEdge >= cookieLeftEdge && pacmanLeftEdge <= cookieRightEdge)) {
                    if (cookie.isVisible()) {
                        this.score += cookie.getValue();
                        this.cookiesEaten++;
                    }
                    cookie.hide();
                }
            } else {
                // pacman goes up
                if ((cookieCenterX >= pacmanLeftEdge && cookieCenterX <= pacmanRightEdge) && (pacmanBottomEdge >= cookieTopEdge && pacmanBottomEdge <= cookieBottomEdge)) {
                    if (cookie.isVisible()) {
                        this.score += cookie.getValue();
                        this.cookiesEaten++;
                    }
                    cookie.hide();
                }
                // pacman goes down
                if ((cookieCenterX >= pacmanLeftEdge && cookieCenterX <= pacmanRightEdge) && (pacmanTopEdge <= cookieBottomEdge && pacmanTopEdge >= cookieTopEdge)) {
                    if (cookie.isVisible()) {
                        this.score += cookie.getValue();
                        this.cookiesEaten++;
                    }
                    cookie.hide();
                }
            }
            this.scoreBoard.getScore().setText("Score: " + this.score);
            if (this.cookiesEaten == this.cookieSet.size() && !gameEnded) {
            	System.out.println("Calling end game");
                this.endGame();
            }
        }
    }

    /**
     * Checks if pacman is touching a ghost, if so call lifeLost() 
     */
    public void checkGhostCoalition() {
        double pacmanCenterY = pacman.getCenterY();
        double pacmanCenterX = pacman.getCenterX();
        double pacmanLeftEdge = pacmanCenterX - pacman.getRadius();
        double pacmanRightEdge = pacmanCenterX + pacman.getRadius();
        double pacmanTopEdge = pacmanCenterY - pacman.getRadius();
        double pacmanBottomEdge = pacmanCenterY + pacman.getRadius();
        for (Ghost ghost : ghosts) {
            double ghostLeftEdge = ghost.getX();
            double ghostRightEdge = ghost.getX() + ghost.getWidth();
            double ghostTopEdge = ghost.getY();
            double ghostBottomEdge = ghost.getY() + ghost.getHeight();
            if ((pacmanLeftEdge <= ghostRightEdge && pacmanLeftEdge >= ghostLeftEdge) || (pacmanRightEdge >= ghostLeftEdge && pacmanRightEdge <= ghostRightEdge)) {
                if ((pacmanTopEdge <= ghostBottomEdge && pacmanTopEdge >= ghostTopEdge) || (pacmanBottomEdge >= ghostTopEdge && pacmanBottomEdge <= ghostBottomEdge)) {
                    lifeLost();
                }
            }
        }
    }
    
    private void checkPacmanEnterDoor() {
    	if(pacman.getCenterX() - pacman.getRadius() < 0) { // left edge
    		pacman.setCenterX(Main.WIDTH - pacman.getRadius());
    	} else if (pacman.getCenterX() + pacman.getRadius() > Main.WIDTH) { // right edge
    		pacman.setCenterX(0 + pacman.getRadius());
    	}
    }
}
