package leaderboard;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class which represents a permanent LeaderBoard
 * @author psyfb2
 */
public class PacmanLeaderBoard implements LeaderBoard {
	class Entry {
		int entryScore;
		String entryName;
	}
	
	private ArrayList<Entry> entrys;
	private int maxNumberOfScores;
	private String fileName;
	
	/**
	 * Constructor, loads the leaderBoard. Leaderboards should be formatted as such: name\nscore\n
	 * E.g. Top score name\n100\nAnother top score name\n90\n etc.
	 * @param fileName File name of the text file where the leaderboard is stored.
	 * @param maxNumberOfScores Number of scores stored within the LeaderBoard. E.g. if you want only top 5 scores to be recorded enter 5.
	 * @throws IOException  fileName not found
	 */
	public PacmanLeaderBoard(String fileName, int maxNumberOfScores) throws IOException {
		entrys = new ArrayList<Entry>();
		this.fileName = fileName;
		this.maxNumberOfScores = maxNumberOfScores;
		this.loadLeaderBoard();
	}
	
	/**
	 * Load entrys from LeaderBoard text file provided in constructor into entrys ArrayList
	 * @throws IOException fileName not found
	 */
	private void loadLeaderBoard() throws IOException {
		BufferedReader scoreTxt = new BufferedReader(new FileReader(fileName));
		String line;
		int i = 1;
		Entry current = null;
		
		while( (line = scoreTxt.readLine()) != null) {
			// every second line contains a score (first lines contain only the name for the score below)
			if(i % 2 == 1) {
				current = new Entry();
				current.entryName = line;
			} else {
				current.entryScore = Integer.parseInt(line);
				entrys.add(current);
			}
			i++;
		}
		scoreTxt.close();
	}
	

	@Override
	public void addToLeaderBoard(String name, int score) {
		Entry newEntry = new Entry();
		newEntry.entryName = name;
		newEntry.entryScore = score;
		
		// if entrys hasn't been fully populated then add a newEntry to entrys for sure
		if(entrys.size() < maxNumberOfScores) {
			int newPos = 0;
			boolean lastScore = true;
			
			for(int i = 0; i < entrys.size(); i++) {
				if(entrys.get(i).entryScore < score) {
					// push every entry starting at i back by 1 to create room for the new entry
					newPos = i;
					entrys.add(null);
					for(int j = entrys.size() - 1; j > i; j--) {
						entrys.set(j, entrys.get(j - 1));
					}
					lastScore = false;
					break;
				}	
			}
			
			if(lastScore) {
				entrys.add(newEntry);
			} else {
				entrys.set(newPos, newEntry);
			}
			return;
		}
		
		// Slightly different algorithm when the LeaderBoard is full
		for(int i = 0; i < entrys.size(); i++) {
			if(entrys.get(i).entryScore < score) {
				// push every entry starting at i back by 1 to create room for the new entry
				for(int j = entrys.size() - 1; j > i; j--) {
					entrys.set(j, entrys.get(j - 1));
				}
				entrys.set(i, newEntry);
				break;
			}	
		}
	}

	@Override
	public int getScoreFromPosition(int pos) {
		if(pos - 1 >= entrys.size()) {
			return -1;
		}
		return entrys.get(pos - 1).entryScore;
	}

	@Override
	public String getNameFromPosition(int pos) {
		if(pos - 1 >= entrys.size()) {
			return null;
		}
		return entrys.get(pos - 1).entryName;
	}
	
	@Override
	public int length() {
		return entrys.size();
	}
	
	@Override
	public void saveLeaderBoard() throws IOException {
		BufferedWriter scoreTxt = new BufferedWriter(new FileWriter(fileName));
		for(Entry e : entrys) {
			scoreTxt.write(e.entryName);
			scoreTxt.newLine();
			scoreTxt.write(Integer.toString(e.entryScore));
			scoreTxt.newLine();
		}
		scoreTxt.close();
	}

}
