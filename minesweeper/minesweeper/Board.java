package minesweeper;

import java.io.Serializable;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.Formatter;
import java.util.ArrayList;
import java.util.Arrays;

import static minesweeper.Difficulty.*;
import static minesweeper.Utils.*;

class Board implements Serializable {
	public Board(Difficulty difficulty) {
		diff = difficulty;
		if (diff == EASY) {
			size = 8;
			mines = 10;
			ROW_COL = Pattern.compile("^[a-h][1-8]$");
		} else if (diff == MEDIUM) {
			size = 16;
			mines = 40;
			ROW_COL = Pattern.compile("^[a-p](1[0-6]|[1-9])$");
		} else if (diff == HARD) {
			size = 22;
			mines = 100;
			ROW_COL = Pattern.compile("^[a-x](2[0-2]|1[0-9]|[0-9])$");
		} else {
			throw new IllegalArgumentException("invalid difficulty");
		}
		boardState = new Square[size][size];
		flagCounter = 0;
		squaresRevealed = 0;
	}
	
	void init(String sq) {
		initialize(col(sq), row(sq));
	}
	
	/** Places a zero-value square at row R, column C, and
	 * then places rigged squares at random locations and fills
	 * the board in with safe squares.
	 * @param c
	 * @param r
	 */
	private void initialize(int c, int r) {	
		set(c, r, new Square(0));
		placeMines(c, r);
		placeSquares();
	}
	
	/** Makes a move. If the target square is null, 
	 * then the board gets initialized. Otherwise either
	 * flags or reveals the target square.
	 * @param c
	 * @param r
	 * @param flagging
	 */
	void makeMove(int c, int r, boolean flagging) {
		if (flagging) {
			Square target = get(c, r);
			if (target != null) {
				if (target.isFlagged()) {
					target.flag();
					flagCounter--;
					return;
				}
				target.flag();
				if (!target.isFlagged()) {
					return;
				}
				flagCounter++;
			} else {
				initialize(c, r);
				target = get(c, r);
				target.flag();
				flagCounter++;
			}
		} else {
			Square target = get(c, r);
			if (target != null) {
				reveal(c, r);
			} else {
				initialize(c, r);
				reveal(c, r);
			}
		}
	}
	
	void makeMove(String sq, boolean flagging) {
		makeMove(col(sq), row(sq), flagging);
	}
	
	boolean mineRevealed() {
		for (int r = 1; r <= size; r++) {
			for (int c = 1; c <= size; c++) {
				try {
					if (get(c, r).hasMine() && get(c, r).isRevealed()) {
						isWon = false;
						return true;
					}
				} catch (NullPointerException e) {
					/* do nothing */
				}
			}
		}
		return false;
	}
	
	boolean safeSquaresRevealed() {
		boolean req = squaresRevealed == size * size - mines;
		if (req) {
			isWon = true;
		}
		return req;
	}
	
	/** Places mines in a random square, but not on a square
	 * whose coordinates are r, c, and not on any square that's
	 * adjacent.
	 * @param c
	 * @param r
	 */
	private void placeMines(int c, int r) {
		Random random = new Random();
		ArrayList<int[]> adj = getAdjCoords(c, r);

		for (int counter = mines; counter > 0; counter--) {
			int row = random.nextInt(size) + 1, col = random.nextInt(size) + 1;
			int[] adjCoord = {row, col};
			if (containsCoords(adjCoord, adj) || get(col, row) != null) {

				System.out.println("shouldn't be here");
				
				counter++;
			} else {
				set(col, row, new Square());
			}
		}
	}
	
	/** Checks if a given set of COORDS is in an array LIST
	 * of coordinates.
	 */
	private boolean containsCoords(int[] coords, 
			ArrayList<int[]> list) {
		int counter = 0;
		for (int[] elem : list) {
			if (Arrays.equals(coords, elem)) {
				counter++;
			}
		}
		return counter != 0;
	}
	
	private void placeSquares() {
		for (int r = 1; r <= size; r++) {
			for (int c = 1; c <= size; c++) {
				if (get(c, r) == null) {
					set(c, r, new Square(getAdjMines(c, r)));
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
		ArrayList<Square> adjSquares = getAdjSquares(c, r);
		for (Square sq : adjSquares) {
			if (sq != null) {
				if (sq.hasMine()) {
					counter++;
				}
			}
		}
		return counter;
	}
	
	private ArrayList<Square> getAdjSquares(int c, int r) {
		ArrayList<Square> result = new ArrayList<>();
		for (int dc = 1; Math.abs(dc) <= 1; dc--) {
			for (int dr = 1; Math.abs(dr) <= 1; dr--) {
				Square target = get(c + dc, r + dr);
				if (target != null) {
					result.add(target);
				}
			}
		}
		return result;
	}
	
	/** Puts coordinates of squares adjacent to square at
	 * row R, column C into a list.
	 * @param c
	 * @param r
	 * @return
	 */
	ArrayList<int[]> getAdjCoords(int c, int r) {
		//TODO: after testing, make this method private
		ArrayList<Square> adj = getAdjSquares(c, r);
		ArrayList<int[]> result = new ArrayList<>();
		for (int i = 0; i < adj.size(); i++) {
			result.add(new int[2]);
		}
		assert adj.size() == result.size();
		int i = 0;
		for (int dc = 1; Math.abs(dc) <= 1; dc--) {
			for (int dr = 1; Math.abs(dr) <= 1; dr--) {					
				int row = r + dr;
				int col = c + dc;
				if (inBounds(col, row)) {
					result.get(i)[0] = row;
					result.get(i)[1] = col;
					i++;
				}
				if (i >= result.size()) {
					return result;
				}
			}
		}
		return result;
	}
	
	/** Reveals square at column C, row R. If the square's
	 * value is 0, recursively reveals all surrounding squares
	 * and all squares surrounding them, if they're value-zero
	 * squares as well.
	 * @param c
	 * @param r
	 */
	private void reveal(int c, int r) {
		Square square = get(c, r);
		if (square.value() != 0) {
			square.reveal();
			squaresRevealed++;
			return;
		} else {
			square.reveal();
			ArrayList<int[]> adj = getAdjCoords(c, r);
			for (int[] coords : adj) {
				try {
					if (!get(coords[1], coords[0]).isRevealed()) {
						reveal(coords[1], coords[0]);
					}
				} catch (IndexOutOfBoundsException | NullPointerException e) {
					/* do nothing */
				}
			}
		}
	}
	
	private boolean inBounds(int c, int r) {
		return (1 <= c && c <= size) && (1 <= r && r <= size);
	}
	
	/** Sets the square at column C, row R to be SQUARE.
	 * Throws an exception if the coordinates are out of bounds.
	 * @param c
	 * @param r
	 * @param square
	 */
	private void set(int c, int r, Square square) {
		try {
			boardState[r - 1][c - 1] = square;
		} catch (IndexOutOfBoundsException e) {			
			return;
		}
	}

	/** Returns the contents of column C and row R, where
	 * C, R <= size and C, R >= 1.
	 * @param c
	 * @param r
	 * @return
	 */
	Square get(int c, int r) {
		try {
			return boardState[r - 1][c - 1];
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
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
		if (sq.length() == 3) {
			return Integer.parseInt(sq.substring(1));
		}
		return sq.charAt(1) - '0';
	}
	
	boolean isWon() {
		return isWon;
	}
	
	boolean allRevealed() {
		try {
			int counter = 0;
			for (int r = 1; r <= size; r++) {
				for (int c = 1; c <= size; c++) {
					if (get(c, r).isRevealed() && !get(c, r).hasMine()) {
						counter++;
					}
				}
			}
			boolean req = counter == size * size - mines;
			if (req) {
				isWon = true;
			}
			return req;
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	@Override
	public String toString() {
		Formatter out = new Formatter();
		out.format("===%n");
		for (int r = size; r >= 1; r--) {
			if (r < 10) {
				out.format(" " + Integer.toString(r) + " ");
			} else {
				out.format(Integer.toString(r) + " ");
			}
			for (int c = 1; c <= size; c++) {
				if (get(c, r) == null) {
					out.format("- ");
				} else {
					out.format("%s ", get(c, r).toString());
				}
			}
			out.format("%n");
		}
		out.format("  ");
		final String alphabet = "abcdefghijklmnopqrstuvwxyz";
		out.format(" ");
		for (int i = 0; i < size; i++) {
			out.format(alphabet.charAt(i) + " ");
		}
		out.format("  ");
		out.format("%n");
		out.format("Flags left: %d", mines - flagCounter);
		out.format("%n");
		return out.toString();
	}
	
	/** The number of flags in use. */
	private int flagCounter;
	
	/** The number of non-rigged squares revealed. */
	private int squaresRevealed;
	
	/** The pattern describing a valid square designator in
	 * the form cr.
	 */
	final Pattern ROW_COL;
	
	/** The difficulty of this board. */
	private final Difficulty diff;
	
	/** The internal representation of the board. */
	private Square[][] boardState;
	
	/** The size of the board. */
	private final int size;
	
	/** The number of mines on the board. */
	private final int mines;
	
	/** Whether the game has been won or lost. */
	private boolean isWon;
}
