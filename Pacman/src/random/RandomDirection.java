package random;

import java.util.Random;

/**
 * Responsible for producing random outputs such as a random direction or random boolean value.
 * @author psyfb2
 */
public class RandomDirection {
	Random rand;
	
	public RandomDirection() {
		rand = new Random();
	}
	
	/**
     * Generates a random direction.
     * Used within createAnimation() method.
     * @param exclude1 direction to exclude, should be either "left", "right", "up", "down"
     * @param exclude2 second direction to exclude, should be either "left", "right", "up", "down"
     * @return a random direction which is either "left", "right", "up", "down"
     * and the random direction is not equal to exclude1 or exclude2
     */
    public String getRandomDirection(String exclude1, String exclude2) { 
        String[] directions = {"left", "right", "up", "down"};
        int rnd = rand.nextInt(directions.length);
        while (directions[rnd].equals(exclude1) || directions[rnd].equals(exclude2)) {
            rnd = rand.nextInt(directions.length);
        }
        return directions[rnd];
    }

    /**
     * @return random boolean value
     */
    public boolean getRandomBoolean() {
        return rand.nextBoolean();
    }
}
