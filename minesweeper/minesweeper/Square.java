package minesweeper;

import java.io.Serializable;

/** Represents a single square on the Minesweeper board.
 * 
 * @author joshuagoldwasser
 *
 */
public class Square implements Serializable {

	private static final int MINE_VALUE = 9;
	
	/** Creates an unrigged, unrevealed, unflagged square
	 * with an associated value.
	 * @param val
	 */
	Square(int val) {
		revealed = false;
		flagged = false;
		hasMine = false;
		value = val;
	}
	
	/** Creates a rigged, unrevealed, unflagged square. */
	Square() {
		revealed = false;
		flagged = false;
		hasMine = true;
		value = MINE_VALUE;
	}
	
	int value() {
		return value;
	}
	
	/** Reveals an unrevealed square. Does nothing if
	 * the square is already revealed.
	 */
	void reveal() {
		if (revealed) {
			return;
		}
		revealed = true;
	}
	
	/** Changes the orientation of an unrevealed square from
	 * flagged to unflagged or vice versa.
	 */
	void flag() {
		if (revealed) {
			System.err.println("Cannot flag this square.");
			return;
		}
		if (!flagged) {
			flagged = true;
		} else {
			flagged = false;
		}
	}
	
	boolean hasMine() {
		return hasMine;
	}
	
	boolean isRevealed() {
		return revealed;
	}
	
	@Override
	public String toString() {
		if (!revealed) {
			if (flagged) {
				return "f";
			}
			return "-";
		} else {
			if (hasMine) {
				return "X";
			}
			if (value == 0) {
				return " ";
			} else {
				return "" + value;
			}
		}
	}
	
	/** The number of mines adjacent to this square. */
	private final int value;
	
	/** Whether this square has been revealed by the user. */
	private boolean revealed;
	
	/** Whether this square has been flagged by the user. */
	private boolean flagged;
	
	/** Whether this square has a mine. */
	private final boolean hasMine;
}
