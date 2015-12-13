package minesweeper;

/** The game of Minesweeper.
 * 
 * @author joshuagoldwasser
 *
 */
abstract class Game {
	
	protected Game(Difficulty difficulty) {
		diff = difficulty;
		board = new Board(diff);
		playing = true;
	}
	
	abstract void play();
	
	boolean gameOver() {
		return !board.mineRevealed();
	}
	
	Board getBoard() {
		return board;
	}
	
	/** The board of this game. */
	protected Board board;
	
	/** This game's difficulty. */
	private final Difficulty diff;
	
	/** Whether the game is in progress. */
	private boolean playing;
}
