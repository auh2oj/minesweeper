package minesweeper;

import java.util.ArrayList;
import java.util.Arrays;

import static minesweeper.Difficulty.*;

public class UnitTests {

	public static void testGetAdjCoords() {
		Board b = new Board(EASY);
		b.init("b7");
				
		ArrayList<int[]> adj = b.getAdjCoords(2, 7);
		
		for (int[] coord : adj) {
			System.out.println(Arrays.toString(coord));
		}
	}
	
	public static void main(String[] args) {
		//testGetAdjCoords();
		int[] coord = {7, 2};
		int[] adjCoord = {8, 1};
		ArrayList<int[]> list = new ArrayList<>();
		list.add(new int[] {8, 1});
		
		System.out.println(Arrays.toString(adjCoord));
		System.out.println(Arrays.toString(list.get(0)));
		
		System.out.println(list.contains(adjCoord));
		System.out.println(adjCoord.equals(list.get(0)));
		System.out.println(adjCoord == list.get(0));
		System.out.println(Arrays.equals(adjCoord, list.get(0)));
	}
}
