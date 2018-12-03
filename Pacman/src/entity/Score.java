package entity;



import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Stores score and life Text objects for the pacman game
 * @author psyfb2
 */
public class Score {
	//TODO should be public?
    public Text score;
    public Text lifes;

    /**
     * Constructor initialises score and life Text objects and add them to the root
     * @param root Group object to add Text to
     */
    public Score(Group root) {
        this.score = new Text(BarObstacle.THICKNESS * 4, BarObstacle.THICKNESS * 28, "Score: 0");
        this.lifes = new Text(BarObstacle.THICKNESS * 20, BarObstacle.THICKNESS * 28,"Lifes: 3");
        score.setFill(Color.MAGENTA);
        score.setFont(Font.font("Arial", 30));

        lifes.setFill(Color.MAROON);
        lifes.setFont(Font.font("Arial", 30));

        root.getChildren().add(score);
        root.getChildren().add(lifes);
    }
}
