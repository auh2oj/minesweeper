package minesweeper;

import java.util.regex.Pattern;
import java.util.Formatter;
import java.util.Scanner;

import static minesweeper.Utils.*;
import static minesweeper.Difficulty.*;

/** Main class for the Minesweeper game.
 *
 * @author joshuagoldwasser
 */
public class Main{
	
	/** Version designator. */
	public static final String VERSION = "1.0";
	
	/** Pattern designator for args in main. */
	public static final Pattern OPTIONS = 
			Pattern.compile("--text{0,1}");

	public static void main(String... args) {
		String arguments = "";
		for (String s : args) {
			arguments += s;
		}
		if (!OPTIONS.matcher(arguments).matches()) {
			System.err.printf("Usage: java minesweeper.Main [ --text ]");
			System.exit(1);
		}
		
		Main main = new Main();
		main.run(args);
	}
	
	void run(String... args) {
		printSelectDiff();
		
		Game game;
		Difficulty diff;
		
		game = null;
		diff = null;

		while (diff == null) {
			Scanner input = new Scanner(System.in);
			String line = input.nextLine();
			
			switch (line) {
			case "a":
				diff = EASY;
				break;
			case "b":
				diff = MEDIUM;
				break;
			case "c":
				diff = HARD;
				break;
			default:
				System.err.println("Invalid choice, please try again.");
			}
		}
		
		if (contains(args, "--text")) {
			game = new TextGame(diff);
		}
		// TODO: add GUIGame here
		
		game.play();
		
	}
	
	void printSelectDiff() {
		System.out.printf("Minesweeper.  Version %s", VERSION);
		System.out.println();
		System.out.println("Please select a difficulty:");
		System.out.println("(a) Easy");
		System.out.println("(b) Medium");
		System.out.println("(c) Hard");
	}
}