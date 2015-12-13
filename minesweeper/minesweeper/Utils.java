package minesweeper;

public class Utils {
	public static <T> boolean contains(final T[] array, final T val) {
		if (val == null) {
			for (final T elem : array) {
				if (elem == null) {
					return true;
				}
			}
		} else {
			for (final T elem : array) {
				if (elem == val || val.equals(elem)) {
					return true;
				}
			}
		}
		return false;
	}
}
