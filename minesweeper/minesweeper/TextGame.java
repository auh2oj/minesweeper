package minesweeper;

import static minesweeper.Difficulty.*;

import java.util.Scanner;
import java.util.regex.Pattern;

public class TextGame extends Game {	
	
	protected TextGame(Difficulty difficulty) {
		super(difficulty);
		input = new Scanner(System.in);
		if (difficulty == EASY) {
			movePattern = Pattern.compile("^[a-h][1-8]$");
		} else if (difficulty == MEDIUM) {
			movePattern = Pattern.compile("^[a-p](1[0-6]|[1-9])$");
		} else {
			movePattern = null;
			// TODO: fix this case
		}
	}

	@Override
	void play() {
		while (true) {
			display();
			if (gameOver()) {
				return;
			}
			String line = getCommand();
			if (line == null) {
				break;
			}
			if (!movePattern.matcher(line).matches()) {
				switch (line) {
				case "save":
					// TODO: add save case
					break;
				case "load":
					// TODO: add load case
					break;
				case "quit":
					// TODO: add quit case
					break;
				default:
					System.err.println("Unknown command.");
					break;
				}
			}
		}
		
		
	}
	
	private String getCommand() {
		while (true) {
			System.err.print("> ");
			System.err.flush();
			if (!input.hasNext()) {
				break;
			}
			String line = input.nextLine();
			line = line.trim();
			if (!line.isEmpty()) {
				return line;
			}
		}
		return null;
	}
	
	private void display() {
		System.out.println(board.toString());
	}

	/** Source of input commands from the user. */
	private Scanner input;
	
	/** The format of acceptable move inputs. */
	private final Pattern movePattern;
}
