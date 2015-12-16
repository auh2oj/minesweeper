package minesweeper;

import java.util.ArrayList;
import java.util.Arrays;

import static minesweeper.Difficulty.*;

public class UnitTests {

	public static void testGetAdjCoords() {
		Board b = new Board(EASY);
		b.init("a1");
		
		System.out.println(b);
		
		ArrayList<int[]> adj = b.getAdjCoords(1, 1);
		
		for (int[] coord : adj) {
			System.out.println(Arrays.toString(coord));
		}
	}
	
	public static void main(String[] args) {
		testGetAdjCoords();
	}
}
