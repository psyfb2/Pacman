package leaderboard;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.After;
import org.junit.Test;

import leaderboard.LeaderBoard;
import leaderboard.PacmanLeaderBoard;

public class PacmanLeaderBoardTest {
	LeaderBoard l;
	
	public void init(int numOfScores) {
		// make sure text file is empty
		try {
			BufferedWriter scoreTxt = new BufferedWriter(new FileWriter("./recources/scores/test.txt"));
			scoreTxt.write("");
			for(int i = 0; i < numOfScores; i++) {
				scoreTxt.write("Hello I'm a Score!123");
				scoreTxt.newLine();
				scoreTxt.write(Integer.toString(numOfScores - i));
				scoreTxt.newLine();
			}
			scoreTxt.close();
			l = new PacmanLeaderBoard("./recources/scores/test.txt", 5);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAddingToEmptyScore() {
		init(0);
		l.addToLeaderBoard("high score0", 200);
		assertEquals("high score0", l.getNameFromPosition(1));
		assertEquals(200, l.getScoreFromPosition(1));
	}
	
	@Test
	public void testAddingTo1Score() {
		init(1);
		l.addToLeaderBoard("high score0", 200);
		assertEquals("high score0", l.getNameFromPosition(1));
		assertEquals(200, l.getScoreFromPosition(1));
		
		assertEquals(1, l.getScoreFromPosition(2));
	}
	
	@Test
	public void testAddingTo2Score() {
		init(2);
		l.addToLeaderBoard("123456", 200);
		assertEquals("123456", l.getNameFromPosition(1));
		assertEquals(200, l.getScoreFromPosition(1));
		
		assertEquals(1, l.getScoreFromPosition(3));
		assertEquals(2, l.getScoreFromPosition(2));
	}
	
	@Test
	public void testAddingTo3Score() {
		init(3);
		l.addToLeaderBoard("\"!^£*()£!(!£*)", 200);
		assertEquals("\"!^£*()£!(!£*)", l.getNameFromPosition(1));
		assertEquals(200, l.getScoreFromPosition(1));
		
		assertEquals(1, l.getScoreFromPosition(4));
		assertEquals(2, l.getScoreFromPosition(3));
		assertEquals(3, l.getScoreFromPosition(2));
	}
	
	@Test
	public void testAddingTo4Score() {
		init(4);
		l.addToLeaderBoard("", 200);
		assertEquals("", l.getNameFromPosition(1));
		assertEquals(200, l.getScoreFromPosition(1));
		
		assertEquals(1, l.getScoreFromPosition(5));
		assertEquals(2, l.getScoreFromPosition(4));
		assertEquals(3, l.getScoreFromPosition(3));
		assertEquals(4, l.getScoreFromPosition(2));
	}
	
	@Test
	public void testAddingTo5Score() {
		init(5);
		l.addToLeaderBoard("", 200);
		assertEquals("", l.getNameFromPosition(1));
		assertEquals(200, l.getScoreFromPosition(1));
		
		assertEquals(2, l.getScoreFromPosition(5));
		assertEquals(3, l.getScoreFromPosition(4));
		assertEquals(4, l.getScoreFromPosition(3));
		assertEquals(5, l.getScoreFromPosition(2));
	}
	
	@After 
	public void saveLeaderBoard() {
		init(5);
		try {
			l.saveLeaderBoard();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
