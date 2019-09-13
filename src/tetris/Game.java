package tetris;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;


/**
 * @author ItsNorin
 * @see <a href="http://github.com/ItsNorin">Github</a>
 */
public class Game extends JPanel implements ActionListener {
	private static final long serialVersionUID = -6492931191037187432L;

	public static final int FIELD_WIDTH = 10, FIELD_HEIGHT = 20;
	public static final int TILE_SIZE = 30;
	
	public static final int NUM_RANDOM_PIECES = 6;

	private static final Dimension SCREEN_SIZE = new Dimension(FIELD_WIDTH * TILE_SIZE, FIELD_HEIGHT * TILE_SIZE);
	private static final Point SPAWN_POS = new Point(FIELD_WIDTH/2 - 1, -1);
	
	private Tile[][] field;
	
	// all existing tetriminos
	private static final Tetrimino[] tetriminos = new Tetrimino[] { Tetrimino.I, Tetrimino.J, Tetrimino.L, Tetrimino.O, Tetrimino.S, Tetrimino.Z, Tetrimino.T };
	private Queue<Integer> pieceQue;
	
	private Random r;
	
	private Tetrimino currentPiece;
	private int currentRotation;
	private Point currentPosition;
	
	private Timer tm;
	
	private int score;
	private int level;
	private int speed;
	private int linesCleared;

	private boolean canMove;
	
	public Game() {
		setMinimumSize(SCREEN_SIZE);
		setMaximumSize(SCREEN_SIZE);
		setPreferredSize(SCREEN_SIZE);
		
		field = new Tile[FIELD_WIDTH][FIELD_HEIGHT];
		pieceQue = new LinkedList<>();
		r = new Random();
		tm = new Timer(speed,this);
		score = 0;
		linesCleared = 0;
		canMove = false;
	}

	
	/** Resets everything so that it is ready for a new game */
	public void init() {
		score = 0;
		linesCleared = 0;
		updateLevel();
		// lets game know it should move piece
		tm.setRepeats(true);
		
		for (int x = 0; x < FIELD_WIDTH; x++) {
			for (int y = 0; y < FIELD_HEIGHT; y++) {
					field[x][y] = new Tile();
			}
		}
		
		pieceQue.clear();
		currentPiece = getNextPiece();

		canMove = true;
		setVisible(true);
	}

	/** move piece if possible in given x and y direction
	 * @param dx x offset
	 * @param dy y offset
	 */
	public void move(int dx, int dy) {
		if(canMove) {
			canMove = false;
			if(!collides(dx, dy, currentRotation) && !isPieceOutOfBounds(dx, dy, currentRotation)) {
				currentPosition.x += dx;
				currentPosition.y += dy;
				repaint();
			}
			canMove = true;
		}
	}
	
	
	/** rotates piece if possible in given direction
	 * @param clockwise True if clockwise, false if CCW
	 */
	public void rotate(boolean clockwise) {
		if(canMove) {
			canMove = false;
			int newRotation = currentRotation;
			
			if(clockwise) 
				newRotation++;
			else
				newRotation--;
			
			if(newRotation < 0)
				newRotation += 4;
			else if(newRotation > 3)
				newRotation -= 4;
			
			if(!collides(0, 0, newRotation) && !isPieceOutOfBounds(0, 0, newRotation)) {
				currentRotation = newRotation;
				repaint();
			}
			canMove = true;
		}
	}
	
	public boolean isPaused() {
		return !tm.isRunning();
	}
	
	/** Pauses the game  */
	public void pause() {
		canMove = false;
		tm.stop();
	}

	
	/** Resumes the game */
	public void resume() {
		canMove = true;
		tm.start();
	}
	
	/**sets current piece to next piece in queue
	 * Will ensure there are always NUM_RANDOM_PIECES in pieceQue
	 * @return next piece in pieceQue */
	private Tetrimino getNextPiece() {
		while(pieceQue.size() <= NUM_RANDOM_PIECES) {
			pieceQue.add(r.nextInt(tetriminos.length));
		}
		currentRotation = 0;
		currentPosition = new Point(SPAWN_POS);
		//pieceCount++;
		return tetriminos[pieceQue.poll()];
	}
	
	/**
	 * @param p Point
	 * @param dx x offset
	 * @param dy y offset
	 * @return new point offset by current position and given offset
	 */
	private Point offsetPoint(Point p, int dx, int dy) {
		return new Point(p.x + currentPosition.x + dx, p.y + currentPosition.y + dy);
	}
	
	/**@param dX X offset
	 * @param dY Y offset
	 * @param rotation rotation of piece
	 * @return true if piece collides with anything in field */
	private boolean collides(int dX, int dY, int rotation) {
		for(Point p : currentPiece.getShape()[rotation]) {
			Point cp = offsetPoint(p, dX, dY);
			
			// only check if piece is in play field
			if(cp.y >= 0 && !isTileOutOfBounds(cp)) 
				if(field[cp.x][cp.y].exists())
					return true;
		}
		return false;
	}
	
	 /**@param p Point
	  * @return true if tile is in bounds to the left, right, or bottom of screen */
	private static boolean isTileOutOfBounds(Point p) {
		return p.x < 0 || p.x >= FIELD_WIDTH || p.y >= FIELD_HEIGHT;
	}
	
	/**@param dX x offset of piece
	 * @param dY y offset of piece
	 * @param rotation rotation of piece
	 * @return true if current piece is out of bounds at given offset and rotation */
	private boolean isPieceOutOfBounds(int dX, int dY, int rotation) {
		for (Point p : currentPiece.getShape()[rotation]) {
			if (isTileOutOfBounds(offsetPoint(p, dX, dY)))
				return true;
		}
		return false;
	}
	
	/** @return true if piece has landed*/
	private boolean checkLanding() {
		canMove = false;
		boolean hasLanded = collides(0, 1, currentRotation);
		
		for (Point p : currentPiece.getShape()[currentRotation])
			if (offsetPoint(p, 0, 1).y >= FIELD_HEIGHT)
				hasLanded = true;
		
		
		if(hasLanded) {
			for(int i = 0; i < 4; i++) {
				Point cp = offsetPoint(currentPiece.getShape()[currentRotation][i], 0, 0);
				if(!isTileOutOfBounds(cp)) 
					field[cp.x][cp.y].set(currentPiece.getColor());
			}
			
			score += clearLines();
			
			System.out.print(currentPiece.getName());
			System.out.print(": ");
			System.out.print(score);
			System.out.print(" at lvl ");
			System.out.println(level);
			
			currentPiece = getNextPiece();
		}
		canMove = true;
		return hasLanded;
	}
	
	/**Clears lines on board around current piece
	 * @return score for clearing lines based on level and number of lines cleared
	 */
	private int clearLines() {
		canMove = false;
		int clearedLineCount = 0;
		
		int minPieceYOffset = 100;
		int maxPieceYOffset = 0;
		// get range in which to clear lines
		for(Point p : currentPiece.getShape()[currentRotation]) {
			if(minPieceYOffset > p.y)
				minPieceYOffset = p.y;
			if(maxPieceYOffset < p.y)
				maxPieceYOffset = p.y;
		}
		
		// start from highest line and work down
		for(int y = currentPosition.y + minPieceYOffset; y <= currentPosition.y + maxPieceYOffset; y++) {
			int tileCount = 0;
			for(int x = 0; x < FIELD_WIDTH; x++)
				if(field[x][y].exists())
					tileCount++;
			
			// translate everything above down
			if(tileCount >= FIELD_WIDTH) {
				clearedLineCount++;
				for(int yT = y; yT > 0; yT--) 
					for(int x = 0; x < FIELD_WIDTH; x++) 
						field[x][yT] = field[x][yT-1];
			}
		}
		
		// ensure new tiles are created at top
		// this better fix the damn ascension issue
		for(int i = 0; i < clearedLineCount; i++) 
			for(int x = 0; x < FIELD_WIDTH; x++)
				if(!field[x][i].exists())
					field[x][i] = new Tile();

		linesCleared += clearedLineCount;
		updateLevel();
		
		canMove = true;
		
		switch(clearedLineCount) {
		case 1: 	return 40   * (level + 1);
		case 2: 	return 100  * (level + 1);
		case 3: 	return 300  * (level + 1);
		case 4: 	return 1200 * (level + 1);
		default:	return 0;
		}
	}
	
	/**Make sure current level matches score, and speed matches level 
	 * Sets level, speed, and tm.delay based on lines cleared*/
	private void updateLevel() {
		level = linesCleared / 10;
		speed = (int)(Math.pow(0.8 - ((level - 1)*0.007), level - 1)*1000);
		tm.setDelay(speed);
	} 
	
	
	
	@Override
	public Dimension getMinimumSize() { return SCREEN_SIZE; }

	@Override
	public Dimension getMaximumSize() { return SCREEN_SIZE; }

	@Override
	public Dimension getPreferredSize() { return SCREEN_SIZE; }

	@Override
	public void paintComponent(Graphics g) {
		// paint all in tile
		for (int x = 0; x < FIELD_WIDTH; x++) 
			for (int y = 0; y < FIELD_HEIGHT; y++) 
				field[x][y].paint(g, TILE_SIZE, x, y);

		for (Point p : currentPiece.getShape()[currentRotation]) {
			Point cp = offsetPoint(p,0,0);

			if (!isTileOutOfBounds(cp)) 
				Tile.paint(g, currentPiece.getColor(), TILE_SIZE, cp.x, cp.y);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		checkLanding();
		move(0, 1);
		repaint();
	}

}
