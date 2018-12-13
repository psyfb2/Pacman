package leaderboard;

import java.io.IOException;

/**
 * Abstract Data Type for a LeaderBoard.
 * There are many different ways to implement a permanent LeaderBoard.
 * Dependency Inversion Principle states high level classes should depend on abstractions not implementations.
 * This interface acts as that abstraction.
 * @author psyfb2
 */
public interface LeaderBoard {
	/**
	 * Add an entry to the LeaderBoard in order of score
	 * @param name Name/Nickname of the person who achieved the score
	 * @param score The score they got
	 */
	public void addToLeaderBoard(String name, int score);
	
	/**
	 * Gives you the score for position x. The highest score has position 1, second highest position 2, third highest 3 etc.
	 * @param pos The position to look for.
	 * @return Score for position pos.
	 */
	public int getScoreFromPosition(int pos);
	
	/**
	 * Gives you the name for position x. The highest score has position 1, second highest position 2, third highest 3 etc.
	 * @param pos The position to look for.
	 * @return Name for position pos.
	 */
	public String getNameFromPosition(int pos);
	
	/**
	 * @return Number of scores held within the leaderboard
	 */
	public int length();
	
	/**
	 * Save the contents of the LeaderBoard permanently (e.g. save to text file)
	 * @throws IOException Board could not be saved
	 */
	public void saveLeaderBoard() throws IOException;
	
}
