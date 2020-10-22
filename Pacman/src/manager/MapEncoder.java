package manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Maps are represented as text files, running a run length encode on a map will save memory (e.g. important on old games consoles).
 * This class allows for run length encoding of maps and also decoding.
 * @author psyfb2
 */
public class MapEncoder {
	/**
	 * Perform Run Length Encoding on a map
	 * @param fileName File name of the map (e.g. "./resources/maps/map1.txt")
	 * @throws IOException File name not found
	 */
	public static void RunLengthEncode(String fileName) throws IOException {
		BufferedReader mapTxt = new BufferedReader(new FileReader(fileName));
		String line;
		// Each encoded row will be a string in the ArrayList
		ArrayList<String> encoded = new ArrayList<String>(); 
		
		while( (line = mapTxt.readLine()) != null ) {
			encoded.add(RunLengthEncodeLine(line));
		}
		mapTxt.close();
		
		// now write the encoded version to the text file
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		for(String encodedLine : encoded) {
			writer.write(encodedLine);
			writer.newLine();
		}
		writer.close();
	}
	
	/**
	 * Perform run length encoding on a single String
	 * @param line String to encode 
	 * @return String String corresponding to Run Length Encoded input line
	 */
	public static String RunLengthEncodeLine(String line) {
		StringBuilder encodedLine = new StringBuilder();
		
		for(int i = 0; i < line.length(); i++) {
			// count adjacent chars for the current char
			int adjacentChars = 1;
			while( i < line.length() - 1 && line.charAt(i) == line.charAt(i + 1)) {
				adjacentChars++;
				i++;
			}
			encodedLine.append("" + adjacentChars + line.charAt(i));
		}
		
		return encodedLine.toString();
	}
	
	/**
	 * Checks whether a text file is in the encoded form for Run Length Encoding.
	 * Useful to check whether a text file is encoded, if not then encode it
	 * @param fileName File name of the file
	 * @return True if it is encoded, else false
	 * @throws IOException File name not found
	 */
	public static boolean isEncoded(String fileName) throws IOException {
		BufferedReader mapTxt = new BufferedReader(new FileReader(fileName));
		String line;
		
		while( (line = mapTxt.readLine()) != null ) {
			if(!isEncodedLine(line)) {
				mapTxt.close();
				return false;
			}
		}
		mapTxt.close();
		return true;
	}
	
	/**
	 * Checks whether a String is encoded using Run length Encoded
	 * @param line String to check
	 * @return True if it is encoded, else false
	 */
	public static boolean isEncodedLine(String line) {
		// first char must be a digit
		if(line.length() > 0 && !Character.isDigit(line.charAt(0))) {
			return false;
		}
		
		// no adjacent non digit character exist
		for(int i = 0; i < line.length() - 1; i++) {
			if(!Character.isDigit(line.charAt(i)) && !Character.isDigit(line.charAt(i + 1))) {
				return false;
			}
		}
		return true;
	}
}
