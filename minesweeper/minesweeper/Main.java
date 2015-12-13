package minesweeper;

import java.util.regex.Pattern;

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
		System.out.printf("Minesweeper.  Version %s", VERSION);
	}
}