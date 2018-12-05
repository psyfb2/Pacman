package random;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;

/**
 * Class to generate a new random colour which has not been generated before
 * @author psyfb2
 */
public class RandomColor {
	private List<Color> usedColor;
	Random rand;
	
	/**
	 * Constructor
	 */
	public RandomColor() {
		usedColor = new ArrayList<>();
		rand = new Random();
	}
	
	/**
	 * Generate a random color which has not been generated before
	 * @return color with random RGB values
	 */
	public Color generateRandomColor() {
		Color c;
		do {
			c = Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
		} while(containedWithin(c));
		usedColor.add(c);
		return c;
	}
	
	/**
	 * Clears memory of all previously generated colors, so any color can be generated again
	 */
	public void reset() {
		usedColor.clear();
	}
	
	/**
	 * Checks whether a color has been generated before
	 * @param c - Color to check
	 * @return true if c has been generated before, else false
	 */
	private boolean containedWithin(Color c) {
		// can't use List contains() method because that will compare object references
		// object references for the color are always different, we want to check for the RGB value so use color equals() method
		for(Color co : usedColor) {
			if (co.equals(c))
				return true;
		}
		return false;
	}
	
}
