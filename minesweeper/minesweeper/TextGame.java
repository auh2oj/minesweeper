package minesweeper;

import static minesweeper.Difficulty.*;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class TextGame extends Game {	
	
	protected TextGame(Difficulty difficulty) {
		super(difficulty);
		input = new BufferedReader(new InputStreamReader(System.in));
		if (difficulty == EASY) {
			movePattern = EASY_PATTERN;
		} else if (difficulty == MEDIUM) {
			movePattern = MEDIUM_PATTERN;
		} else {
			movePattern = HARD_PATTERN;
		}
	}

	@Override
	void play() {
		while (true) {
			display();
			if (gameOver()) {
				return;
			}
			
			prompt();

			String line = getCommand();
			if (line == null) {
				break;
			}
			Scanner inp = new Scanner(line);
			if (!movePattern.matcher(line).matches()) {
				switch (inp.next()) {
				case "save":
					// TODO: add save case
					break;
				case "load":
					// TODO: add load case
					break;
				case "quit":
					// TODO: add quit case
					break;
				case "flag":
					String next = inp.next();
					Pattern flagPattern;
					if (movePattern == EASY_PATTERN) {
						flagPattern = Pattern.compile("[a-h][1-8]$");
					} else if (movePattern == MEDIUM_PATTERN) {
						flagPattern = Pattern.compile("[a-p](1[0-6]|[1-9])$");
					} else {
						flagPattern = Pattern.compile("[a-x](2[0-2]|1[0-9]|[0-9])$");
					}
					if (!flagPattern.matcher(next).matches()) {
						System.err.println("Invalid syntax.");
						break;
					} else {
						board.makeMove(next, true);
						break;
					}
				default:
					System.err.println("Unknown command.");
					break;
				}
			} else {
				board.makeMove(line, false);
			}
		}
		
		
	}
	
	private String getCommand() {
		try {
			return input.readLine();
		} catch (IOException e) {
			System.err.println("unexpected I/O error on input");
			return null;
		}
	}
	
	private void prompt() {
		System.out.print("> ");
		System.out.flush();
	}
	
	private void display() {
		System.out.println(board.toString());
	}
	
	/** Reader for user input. */
	private BufferedReader input;
	
	/** The format of acceptable move inputs. */
	private final Pattern movePattern;
	
	/** The format of acceptable move inputs for each difficulty. */
	private static final Pattern EASY_PATTERN = Pattern.compile("^[a-h][1-8]$");
	private static final Pattern MEDIUM_PATTERN = Pattern.compile("^[a-p](1[0-6]|[1-9])$");
	private static final Pattern HARD_PATTERN = Pattern.compile("^[a-x](2[0-2]|1[0-9]|[0-9])$");
}
