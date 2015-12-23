package minesweeper;

import java.util.ArrayList;
import java.util.Arrays;

import static minesweeper.Difficulty.*;

public class UnitTests {

	public static void testGetAdjCoords() {
		Board b = new Board(EASY);
		b.init("c3");
				
		ArrayList<int[]> adj = b.getAdjCoords(3, 3);
		
		for (int[] coord : adj) {
			System.out.println(Arrays.toString(coord));
		}
	}
	
	public static void testInit() {
		Board b = new Board(EASY);
		b.init("c3");
		
		ArrayList<int[]> adj = b.getAdjCoords(3, 3);
		
		for (int[] coord : adj) {
			System.out.println(Arrays.toString(coord));
			System.out.println(b.get(coord[1], coord[0]).hasMine());
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		testGetAdjCoords();
		testInit();

	}
}
