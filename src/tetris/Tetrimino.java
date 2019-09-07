package tetris;

import java.awt.Color;
import java.awt.Point;

/**
 * @author ItsNorin
 * @see <a href="http://github.com/ItsNorin">Github</a>
 */
public class Tetrimino {
	// I-Piece
	public static Tetrimino I = new Tetrimino(
			new Point[][] {
					{ new Point(1, -1), new Point(1, 0), new Point(1, 1), new Point(1, 2) },
					{ new Point(-1, 1), new Point(0, 1), new Point(1, 1), new Point(2, 1) },
					{ new Point(1, -1), new Point(1, 0), new Point(1, 1), new Point(1, 2) },
					{ new Point(-1, 1), new Point(0, 1), new Point(1, 1), new Point(2, 1) } },
			Color.CYAN);

	// J-Piece
	public static Tetrimino J = new Tetrimino(
			new Point[][] { 
					{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2) },
					{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0) } },
			Color.BLUE);

	// L-Piece
	public static Tetrimino L = new Tetrimino(
			new Point[][] { 
					{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2) },
					{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0) } },
			Color.ORANGE);

	// O-Piece
	public static Tetrimino O = new Tetrimino(
			new Point[][] { 
					{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
					{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
					{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
					{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) } },
			Color.YELLOW);
	// S-Piece
	public static Tetrimino S = new Tetrimino(
			new Point[][] { 
					{ new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
					{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
					{ new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
					{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) } },
			Color.GREEN);

	// Z-Piece
	public static Tetrimino Z = new Tetrimino(
			new Point[][] { 
					{ new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
					{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) },
					{ new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
					{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) } },
			Color.RED);

	// T-Piece
	public static Tetrimino T = new Tetrimino(
			new Point[][] { 
					{ new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2) },
					{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2) },
					{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
					{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1) } },
			Color.MAGENTA);

	// shape of tetrimino based on rotation
	// idea from https://gist.github.com/DataWraith/5236083
	private Point[][] shape;
	private Color color;

	private Tetrimino(Point[][] shape, Color c) {
		this.shape = shape;
		this.color = c;
	}

	public Tetrimino(Tetrimino t) {
		this(t.shape, t.color);
	}

	public Point[][] getShape() {
		return shape;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color c) {
		this.color = c;
	}
}
