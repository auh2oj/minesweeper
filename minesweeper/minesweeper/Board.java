package minesweeper;

import java.io.Serializable;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.Formatter;

import static minesweeper.Difficulty.*;

public class Board implements Serializable {
	public Board(Difficulty difficulty) {
		diff = difficulty;
		if (diff == EASY) {
			size = 8;
			mines = 10;
			ROW_COL = Pattern.compile("[a-h][1-8]");
		} else if (diff == MEDIUM) {
			size = 16;
			mines = 40;
			ROW_COL = Pattern.compile("[a-p][1-16]");
		} else if (diff == HARD) {
			size = 64;
			mines = 99;
			ROW_COL = null;
			// TODO: fix this case
		} else {
			throw new IllegalArgumentException("invalid difficulty");
		}
		boardState = new Square[size][size];
	}
	
	private void placeMines() {
		Random r = new Random();
		for (int counter = mines; counter > 0; counter--) {
			int row = r.nextInt(size), col = r.nextInt(size);
			if (this.get(col, row) != null) {
				boardState[row][col] = new Square();
			} else {
				counter++;
			}
		}
	}
	
	private void placeSquares() {
		for (int r = 1; r <= size; r++) {
			for (int c = 1; c <= size; c++) {
				if (get(c, r) == null) {
					boardState[r - 1][c - 1] =
							new Square(getAdjMines(c, r));
				}
			}
		}
	}
	
	/** Returns the number of mines adjacent to the square
	 * at row R, column C.
	 * @param c
	 * @param r
	 * @return
	 */
	private int getAdjMines(int c, int r) {
		int counter = 0;
		Square[] adjSquares = getAdjSquares(c, r);
		for (Square sq : adjSquares) {
			if (sq != null) {
				if (sq.hasMine()) {
					counter++;
				}
			}
		}
		return counter;
	}
	
	private Square[] getAdjSquares(int c, int r) {
		Square[] result = {
				get(c + 1, r + 1), get(c, r + 1), get(c - 1, r + 1),
				get(c + 1, r), get(c - 1, r),
				get(c + 1, r - 1), get(c, r - 1), get(c - 1, r - 1)
		};
		// TODO: fix this so that it skips out of bounds squares
		return result;
	}

	/** Returns the contents of column C and row R, where
	 * C, R <= size and C, R >= 1.
	 * @param c
	 * @param r
	 * @return
	 */
	Square get(int c, int r) {
		return boardState[r - 1][c - 1];
	}
	
	/** Returns the contents of SQ. SQ must have the proper
	 * designation, having the form cr, where c is a letter
	 * and r is a number. Both c and r are bounded by the
	 * board size.
	 * @param sq
	 * @return
	 */
	Square get(String sq) {
		return get(col(sq), row(sq));
	}
	
	int col(String sq) {
		if (!ROW_COL.matcher(sq).matches()) {
			throw new IllegalArgumentException("bad square designator");
		}
		return sq.charAt(0) - 'a' + 1;
	}
	
	int row(String sq) {
		if (!ROW_COL.matcher(sq).matches()) {
			throw new IllegalArgumentException("bad square designator");
		}
		return sq.charAt(1) - '0';
	}
	
	@Override
	public String toString() {
		Formatter out = new Formatter();
		out.format("===%n");
		for (int r = size; r >= 1; r--) {
			out.format(Integer.toString(r) + " ");
			for (int c = 1; c <= size; c ++) {
				out.format("%s ", get(c, r).toString());
			}
			out.format("%n");
		}
		out.format("  ");
		final String alphabet = "abcdefghijklmnopqrstuvwxyz";
		for (int i = 1; i <= size; i++) {
			out.format(alphabet.charAt(i) + " ");
		}
		out.format("  ");
		return out.toString();
	}
	
	/** The pattern describing a valid square designator in
	 * the form cr.
	 */
	final Pattern ROW_COL;
	
	/** The difficulty of this board. */
	private final Difficulty diff;
	
	/** The internal representation of the board. */
	private final Square[][] boardState;
	
	/** The size of the board. */
	private final int size;
	
	/** The number of mines on the board. */
	private final int mines;
}
