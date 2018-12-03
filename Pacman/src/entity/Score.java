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
	// private to enhance encapsulation, access using getters and setters
    private Text score;
    private Text lifes;

    /**
     * Constructor initialises score and life Text objects and add them to the root
     * @param root Group object to add Text to
     */
    public Score(Group root) {
        this.setScore(new Text(BarObstacle.THICKNESS * 4, BarObstacle.THICKNESS * 28, "Score: 0"));
        this.setLifes(new Text(BarObstacle.THICKNESS * 20, BarObstacle.THICKNESS * 28,"Lifes: 3"));
        getScore().setFill(Color.MAGENTA);
        getScore().setFont(Font.font("Arial", 30));

        getLifes().setFill(Color.MAROON);
        getLifes().setFont(Font.font("Arial", 30));

        root.getChildren().add(getScore());
        root.getChildren().add(getLifes());
    }

	public Text getScore() {
		return score;
	}

	public void setScore(Text score) {
		this.score = score;
	}

	public Text getLifes() {
		return lifes;
	}

	public void setLifes(Text lifes) {
		this.lifes = lifes;
	}
}
