package model;

import java.io.Serializable;

/**
 * @author loic and Lucas
 * <p>
 * Coordinates for the model
 */
@SuppressWarnings("serial")
public class Coord implements Serializable {

	@SuppressWarnings("javadoc")
	public int x, y;

	/**
	 * constructor
	 * <p>
	 * 
	 * @param x
	 * @param y
	 */
	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "[x=" + x + ", y=" + y + "]";
	}

	/**
	 * verify if the coordinates are in the chess, between 0 and 7
	 * <p>
	 * 
	 * @param x
	 * @param y
	 *          <p>
	 * @return true if the coordinates are in the chess board
	 */
	public static boolean coordonnees_valides(int x, int y) {
		return ((x <= 7) && (x >= 0) && (y <= 7) && (y >= 0));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coord other = (Coord) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
