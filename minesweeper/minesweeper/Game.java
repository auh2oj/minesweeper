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
	}
	
	abstract void play();
	
	boolean gameOver() {
		return board.mineRevealed()
				|| board.allRevealed();
	}
	
	Board getBoard() {
		return board;
	}
	
	/** The board of this game. */
	protected Board board;
	
	/** This game's difficulty. */
	protected final Difficulty diff;
}
